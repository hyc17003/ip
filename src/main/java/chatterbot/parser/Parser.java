package chatterbot.parser;

import chatterbot.storage.Storage;
import chatterbot.ui.Ui;
import chatterbot.data.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the interpretation of entered user inputs.
 */
public class Parser {

    /**
     * Evaluates entered user inputs and calls the corresponding method.
     * @param userMessage This is the entered user input.
     * @param ui This is what will be returned to the user.
     * @param list This is the current task list.
     * @param storage This is where the file and list contents are edited.
     * @param file This is the file path to retrieve the .txt file
     * @param taskList This is the list of tasks that have been added.
     */

    public static String evaluateCommand(String userMessage, Ui ui, ArrayList<Task> list, Storage storage, String file,
                                       TaskList taskList) {

        String response = "";

        if (userMessage.toLowerCase().equals("bye")) {
            response = ui.showGoodbyeMessage();
        } else if (userMessage.toLowerCase().equals("list")) {
            response = ui.showTaskList(taskList.getList());
        } else if (userMessage.startsWith("mark") && isInteger(userMessage.substring(5))) {
            String toMark = userMessage.substring(5);
            list.get(Integer.parseInt(toMark) - 1).setDone();
            response = ui.showMarked(toMark);
        } else if (userMessage.startsWith("unmark")) {
            String toUnmark = userMessage.substring(7);
            list.get(Integer.parseInt(toUnmark) - 1).setUndone();
            response = ui.showUnmarked(toUnmark);
        } else if (userMessage.startsWith("find")) {
            String toFind = userMessage.substring(5);
            response = ui.showFoundTasks(toFind);
        } else {
            if (userMessage.startsWith("deadline")) {
                try {
                    if (userMessage.length() <= 9) {
                        throw new IllegalArgumentException("No task description");
                    }
                    try {
                        storage.appendToFile(file, userMessage);
                    } catch (IOException e) {
                        System.out.println("Unable to append to file!");
                    }
                    int slashDeadline = userMessage.indexOf("/");
                    String deadlineDescription = userMessage.substring(9, slashDeadline).trim();
                    String deadlineBy = userMessage.substring(slashDeadline + 3).trim();
                    Deadline d = new Deadline(deadlineDescription, deadlineBy);
                    list.add(d);
                    taskList.addTask(d, storage, file);
                    response = ui.showAddedDeadline(d);
                } catch (IllegalArgumentException e) {
                    System.out.println("OOPS!!! Invalid input!");
                }
            } else if (userMessage.startsWith("todo")) {
                try {
                    if (userMessage.length() <= 5) {
                        throw new IllegalArgumentException("No task description");
                    }
//                    try {
//                        storage.appendToFile(file, userMessage);
//                    } catch (IOException e) {
//                        System.out.println("Unable to append to file!");
//                    }
                    Todo td = new Todo(userMessage.substring(5));
                    list.add(td);
                    taskList.addTask(td, storage, file);
                    response = ui.showAddedTodo(td);
                } catch (IllegalArgumentException e) {
                    System.out.println("OOPS!!! Invalid input! " + e.getMessage() + ".");
                }
            } else if (userMessage.startsWith("event")) {
                try {
                    if (userMessage.length() <= 6) {
                        throw new IllegalArgumentException("No task description");
                    }
                    try {
                        storage.appendToFile(file, userMessage);
                    } catch (IOException e) {
                        System.out.println("Unable to append to file!");
                    }
                    String[] eventSplit = userMessage.split("/");
                    String eventDescription = eventSplit[0].substring(6);
                    String eventTo = eventSplit[1].substring(5);
                    String eventFrom = eventSplit[2].substring(3);
                    Event e = new Event(eventDescription, eventTo, eventFrom);
                    list.add(e);
                    taskList.addTask(e, storage, file);
                    response = ui.showAddedEvent(e);
                } catch (IllegalArgumentException e) {
                    System.out.println("OOPS!!! Invalid input!");
                }
            } else if (userMessage.startsWith("delete") && isInteger(userMessage.substring(7))) {
                response = ui.showDeleted(userMessage);
                list.remove((Integer.parseInt(userMessage.substring(7))) - 1);
                taskList.deleteTask(((Integer.parseInt(userMessage.substring(7))) - 1), storage, file);
//                try {
//                    storage.writeToFile(file, taskList.convertToString(list));
//                } catch (IOException e) {
//                    System.out.println("Error!");
//                }
            } else {
                response = ui.showUnknownCommand(userMessage);
            }
        }
        return response;
    }

    /**
     * Returns whether an entered string is an integer.
     * @param str This is the entered string.
     * @return boolean This is representative of whether the string is an integer or not.
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}