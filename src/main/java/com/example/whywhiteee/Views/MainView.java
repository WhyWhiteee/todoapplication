package com.example.whywhiteee.Views;

import com.example.whywhiteee.Models.Tasks;
import com.example.whywhiteee.Repositories.TasksRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private final transient TasksRepository tasksRepository;

    public MainView(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;

        setupComponents();
    }

    private void setupComponents() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        final H1 title = new H1("Welcome to the Tasks App");
        add(title);

        //кнопка для создания новой задачи
        Button createTaskButton = new Button("Создать задачу", e -> {
            NewTaskDialog newTaskDialog = new NewTaskDialog(tasksRepository, () -> getUI().ifPresent(ui -> ui.getPage().reload()));
            newTaskDialog.open();
        });
        add(createTaskButton);

        //tabs
        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Доступные", new VerticalLayout(getTasks(tasksRepository.findByCompleted(false))));
        tabSheet.add("Завершенные", new VerticalLayout(getTasks(tasksRepository.findByCompleted(true))));
        //tabSheet.add("Архив", new VerticalLayout(getTasks(tasksRepository.findByArchived(true))));
        add(tabSheet);
    }

    //получение всех задач
    private Grid<Tasks> getTasks(List<Tasks> tasksList) {

        Grid<Tasks> grid = new Grid<>(Tasks.class, false);

        grid.addColumn(new ComponentRenderer<>(tasks -> {
            //кнопка завершения
            String completeButtonText = tasks.getCompleted() ? "Вернуть" : "Завершить";
            Button completeButton = new Button(completeButtonText);
            completeButton.getStyle().set("background", "transparent").set("cursor", "pointer");
            completeButton.addClickListener(event -> {
                tasks.setCompleted(!tasks.getCompleted());
                tasksRepository.save(tasks);
                //обновление ui
                getUI().ifPresent(ui -> ui.getPage().reload());
            });
            //кнопка удаления
            Button deleteButton = new Button("Удалить");
            deleteButton.getStyle().set("background", "transparent").set("cursor", "pointer");
            deleteButton.addClickListener(event -> {
                //tasks.setArchived(true);
                //tasksRepository.save(tasks);
                tasksRepository.delete(tasks);

                //обновление ui
                getUI().ifPresent(ui -> ui.getPage().reload());
            });

            VerticalLayout layout = new VerticalLayout(completeButton, deleteButton);
            layout.getStyle().set("gap", "5px");
            return layout;
        })).setHeader("Действия").setAutoWidth(true);

        grid.addColumn(Tasks::getTitle).setHeader("Название").setAutoWidth(true);
        grid.addColumn(Tasks::getDescription).setHeader("Описание").setAutoWidth(true);


        grid.setItems(tasksList);
        grid.setAllRowsVisible(true);

        return grid;
    }

}
