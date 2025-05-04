package com.example.whywhiteee.Controllers;

import com.example.whywhiteee.Models.Tasks;
import com.example.whywhiteee.Models.Users;
import com.example.whywhiteee.Services.TasksService;
import com.example.whywhiteee.Services.UsersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.List;

public class NewTaskDialog extends Dialog {

    public NewTaskDialog(TasksService taskService, UsersService usersService, Runnable onTaskSaved) {
        setHeaderTitle("Новая задача");
        TextField titleField = new TextField("Название задачи");
        TextField descriptionField = new TextField("Описание задачи");
        DatePicker datePicker = new DatePicker("Срок выполнения");
        LocalDate currentDate = LocalDate.now();

        List<Users> users = usersService.getAllUsers();
        ComboBox<Users> comboBoxExecutor = new ComboBox<>("Выберите исполнителя");
        comboBoxExecutor.setItems(users);
        comboBoxExecutor.setItemLabelGenerator(Users::getName);
        ComboBox<Users> comboBoxCreator = new ComboBox<>("Выберите создателя");
        comboBoxCreator.setItems(users);
        comboBoxCreator.setItemLabelGenerator(Users::getName);

        Button saveButton = new Button("Сохранить", event -> {
            if (!titleField.getValue().trim().isBlank() && !descriptionField.getValue().trim().isBlank()) {
                Tasks task = new Tasks();
                task.setTitle(titleField.getValue());
                task.setDescription(descriptionField.getValue());
                task.setCompleted(false);
                task.setArchived(false);
                task.setDeadline(datePicker.getValue());
                task.setCreateDate(currentDate);
                task.setExecutor(comboBoxExecutor.getValue());
                task.setCreator(comboBoxCreator.getValue());
                taskService.saveTask(task);
                onTaskSaved.run();
                close();
            }
        });
        Button cancelButton = new Button("Отмена", e -> close());
        getFooter().add(cancelButton, saveButton);
        VerticalLayout layout = new VerticalLayout(titleField, descriptionField, datePicker, comboBoxCreator, comboBoxExecutor);
        add(layout);
    }
}
