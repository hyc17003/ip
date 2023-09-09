package chatterbot.ui;

import chatterbot.data.Deadline;
import chatterbot.data.Event;
import chatterbot.data.Task;
import chatterbot.data.Todo;

import java.util.ArrayList;

public class Ui {

    private static ArrayList<Task> list;

    public Ui(ArrayList<Task> list) {
        this.list = list;
    }

    public static void showWelcomeMessage() {
        String logo = "ChatterBot";
        System.out.println("Hello! I'm " + logo + "\nWhat can I do for you?");
    }

    public static void showGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void showTaskList() {
        System.out.println("Here are the tasks in your list:");
        for (Task t : list) {
            System.out.println((list.indexOf(t) + 1) + ". "
                    + t.toString());
        }
    }

    /**
     * Prints the tasks in list format that contain the entered keyword.
     * @param toFind This is the entered keyword.
     */
    public static void showFoundTasks(String toFind) {
        ArrayList<Task> foundTasksArray = new ArrayList<Task>();
        for (Task t : list) {
            if (t.description.contains(toFind)) {
                foundTasksArray.add(t);
            }
        }
        if (foundTasksArray.isEmpty()) {
            System.out.println("No matching tasks!");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (Task t : foundTasksArray) {
                System.out.println((foundTasksArray.indexOf(t) + 1) + ". "
                        + t.toString());
            }
        }
    }

    public static void showMarked(String toMark) {
        System.out.println("Nice! I've marked this task as done:\n" + "[X] " + list.get(Integer.parseInt(toMark) - 1).description);
    }

    public static void showUnmarked(String toUnmark) {
        System.out.println("OK, I've marked this task as not done yet:\n" + "[ ] " + list.get(Integer.parseInt(toUnmark) - 1).description);
    }

    public static void showAddedDeadline(Deadline d) {
        System.out.println("Got it. I've added this task:\n" + d.toString() + "\nNow you have " + list.size() + " tasks in the list.");
    }

    public static void showAddedTodo(Todo td) {
        System.out.println("Got it. I've added this task:\n" + td.toString() + "\nNow you have " + list.size() + " tasks in the list.");
    }

    public static void showAddedEvent(Event e) {
        System.out.println("Got it. I've added this task:\n" + e.toString() + "\nNow you have " + list.size() + " tasks in the list.");
    }

    public static void showDeleted(String userMessage) {
        System.out.println("Noted. I've removed this task:\n" + list.get((Integer.parseInt(userMessage.substring(7)))-1) + "\nNow you have " + (list.size() - 1) + " tasks in the list.");
    }

    public static void showUnknownCommand(String userMessage) {
        System.out.println("Unknown command: " + userMessage);
    }
}
