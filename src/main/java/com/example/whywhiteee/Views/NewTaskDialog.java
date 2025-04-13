package com.example.whywhiteee.Views;

import com.example.whywhiteee.Models.Tasks;
import com.example.whywhiteee.Repositories.TasksRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

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
