package com.example.whywhiteee.Controllers;

import com.example.whywhiteee.Models.Tasks;
import com.example.whywhiteee.Services.TasksService;
import com.example.whywhiteee.Services.UsersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private final transient TasksService tasksService;
    private final transient UsersService usersService;

    public MainView(TasksService taskService, UsersService usersService) {
        this.tasksService = taskService;
        this.usersService = usersService;
        setupComponents();
    }

    private void setupComponents() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        final H1 title = new H1("Welcome to the Tasks App");
        add(title);

        //кнопка для создания новой задачи
        Button createTaskButton = new Button("Создать задачу", e -> {
            NewTaskDialog newTaskDialog = new NewTaskDialog(tasksService, usersService, () -> getUI().ifPresent(ui -> ui.getPage().reload()));
            newTaskDialog.open();
        });
        add(createTaskButton);

        //tabs
        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Доступные", new VerticalLayout(getTasks(tasksService.getTasksByCompletedAndArchived(false), "Доступные")));
        tabSheet.add("Завершенные", new VerticalLayout(getTasks(tasksService.getTasksByCompletedAndArchived(true), "Завершенные")));
        tabSheet.add("Архив", new VerticalLayout(getTasks(tasksService.getTasksByArchived(true), "Архив")));
        add(tabSheet);
    }

    //получение всех задач
    private Grid<Tasks> getTasks(List<Tasks> tasksList, String tabType) {

        Grid<Tasks> grid = new Grid<>(Tasks.class, false);

        grid.addColumn(Tasks::getTitle).setHeader("Название").setAutoWidth(true);
        grid.addColumn(Tasks::getDescription).setHeader("Описание").setAutoWidth(true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        grid.addColumn(task -> task.getDeadline() != null
                ? task.getDeadline().format(formatter) : "").setHeader("Срок выполнения").setAutoWidth(true);
        grid.addColumn(task -> task.getCreateDate() != null
                ? task.getCreateDate().format(formatter) : "").setHeader("Дата создания").setAutoWidth(true);

        grid.addColumn(new ComponentRenderer<>(task -> {
            VerticalLayout layout = new VerticalLayout();
            layout.getStyle().set("gap", "5px");

            if (!tabType.equals("Архив")) {
                //кнопка завершения
                String completeButtonText = task.getCompleted() ? "Отменить завершение" : "Завершить";
                Button completeButton = new Button(completeButtonText);
                completeButton.getStyle().set("background", "transparent").set("cursor", "pointer");
                completeButton.addClickListener(event -> {
                    task.setCompleted(!task.getCompleted());
                    tasksService.saveTask(task);
                    //обновление ui
                    getUI().ifPresent(ui -> ui.getPage().reload());
                });
                layout.add(completeButton);
            } else {
                Button restoreButton = new Button("Вернуть из архива");
                restoreButton.getStyle().set("background", "transparent").set("cursor", "pointer");
                restoreButton.addClickListener(event -> {
                    task.setArchived(false);
                    tasksService.saveTask(task);
                    getUI().ifPresent(ui -> ui.getPage().reload());
                });
                layout.add(restoreButton);
            }

            //кнопка удаления
            String deleteButtonText = task.getArchived() ? "Удалить" : "Архивировать";
            Button deleteButton = new Button(deleteButtonText);
            deleteButton.getStyle().set("background", "transparent").set("cursor", "pointer");
            deleteButton.addClickListener(event -> {
                if (task.getArchived()) {
                    tasksService.deleteTask(task);
                } else {
                    task.setArchived(true);
                    tasksService.saveTask(task);
                }
                //обновление ui
                getUI().ifPresent(ui -> ui.getPage().reload());
                });
            layout.add(deleteButton);
            return layout;
        })).setHeader("Действия").setAutoWidth(true);

        grid.setItems(tasksList);
        grid.setAllRowsVisible(true);

        return grid;
    }

}