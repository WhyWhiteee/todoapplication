package com.example.whywhiteee.views;

import com.example.whywhiteee.Tasks;
import com.example.whywhiteee.TasksRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.scheduling.config.Task;

import javax.swing.*;

public class NewTaskDialog extends Dialog {

    public NewTaskDialog(TasksRepository tasksRepository, Runnable onTaskSaved) {
        setHeaderTitle("Новая задача");

        TextField titleField = new TextField("Название задачи");
        TextField descriptionField = new TextField("Описание задачи");

        Button saveButton = new Button("Сохранить", event -> {
            if (!titleField.getValue().trim().isBlank() && !descriptionField.getValue().trim().isBlank()) {
                Tasks task = new Tasks();
                task.setTitle(titleField.getValue());
                task.setDescription(descriptionField.getValue());
                task.setCompleted(false);
                tasksRepository.save(task);
                onTaskSaved.run();
                close();
            }
        });
        Button cancelButton = new Button("Отмена", e -> close());
        getFooter().add(cancelButton, saveButton);
        VerticalLayout layout = new VerticalLayout(titleField, descriptionField);
        add(layout);


    }
}
