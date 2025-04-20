package com.example.whywhiteee.Controllers;

import com.example.whywhiteee.Models.Tasks;
import com.example.whywhiteee.Services.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;

public class NewTaskDialog extends Dialog {

    public NewTaskDialog(TaskService taskService, Runnable onTaskSaved) {
        setHeaderTitle("Новая задача");

        TextField titleField = new TextField("Название задачи");
        TextField descriptionField = new TextField("Описание задачи");
        DatePicker datePicker = new DatePicker("Срок выполнения");
        LocalDate currentDate = LocalDate.now();

        Button saveButton = new Button("Сохранить", event -> {
            if (!titleField.getValue().trim().isBlank() && !descriptionField.getValue().trim().isBlank()) {
                Tasks task = new Tasks();
                task.setTitle(titleField.getValue());
                task.setDescription(descriptionField.getValue());
                task.setCompleted(false);
                task.setArchived(false);
                task.setDeadline(datePicker.getValue());
                task.setCreateDate(currentDate);
                taskService.saveTask(task);
                onTaskSaved.run();
                close();
            }
        });
        Button cancelButton = new Button("Отмена", e -> close());
        getFooter().add(cancelButton, saveButton);
        VerticalLayout layout = new VerticalLayout(titleField, descriptionField, datePicker);
        add(layout);
    }
}
