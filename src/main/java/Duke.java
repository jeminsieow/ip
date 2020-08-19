import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        showGreeting();
        String[] inputArr = getInputArr();
        String command = inputArr[0];
        while (!command.equals("bye")) {
            switch (command) {
            case "list":
                showList();
                break;
            case "done":
                doneTask(inputArr);
                break;
            case "deadline":
                addTask("deadline", inputArr);
                break;
            case "event":
                addTask("event", inputArr);
                break;
            case "todo":
                addTask("todo", inputArr);
                break;
            }
            inputArr = getInputArr();
            command = inputArr[0];
        }
        showGoodbye();
    }

    private static String[] getInputArr() {
        String input = scanner.nextLine();
        return input.split("\\s+");
    }

    private static void showGreeting() {
        String message = "Eh what's up\n"
                + "What do you want?";
        System.out.println(wrapMessage(message));
    }

    private static void showGoodbye() {
        String message = "Alright I'll see you around!";
        System.out.println(wrapMessage(message));
    }

    private static void showList() {
        String message = "Here are the tasks in your list:\n";
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            message += (i + 1)
                    + ". "
                    + task
                    +"\n";
        }
        System.out.println(wrapMessage(message));
    }

    private static void addTask(String type, String[] inputArr) {
        String dateTime = "";
        Task task;

        String desc =  getTaskDescription(inputArr);

        if (type.equals("deadline") || type.equals("event")) {
            dateTime = getTaskTimeDate(inputArr);
        }

        switch (type) {
        case "deadline":
            task = new Deadline(desc, dateTime);
            break;
        case "event":
            task = new Event(desc, dateTime);
            break;
        case "todo":
            task = new Todo(desc);
            break;
        default:
            throw new IllegalStateException("Unexpected type value: " + type);
        }

        taskList.add(task);

        String message = "Got it. I've added this task:\n"
                + task
                + "\n"
                + "Now you have "
                + taskList.size()
                + " tasks in the list.";
        System.out.println(wrapMessage(message));
    }

    private static String getTaskDescription(String[] inputArr) {
        String desc = "";
        int i = 1;
        while ((i < inputArr.length) && (!inputArr[i].contains("/"))) {
            // Get description of the task, which is after the command in inputArr
            desc = desc + inputArr[i] + " ";
            i++;
        }
        return desc.substring(0, desc.length() - 1);
    }

    private static String getTaskTimeDate(String[] inputArr) {
        String dateTime = "";
        int i = 0;
        while (!inputArr[i].contains("/")) {
            // Get description of the task, which is after the command in inputArr
            i++;
        }
        i++;
        while (i < inputArr.length) {
            dateTime = dateTime + inputArr[i] + " ";
            i++;
        }
        return dateTime.substring(0, dateTime.length() - 1);
    }

    private static void doneTask(String[] inputArr) {
        String lastChar = inputArr[inputArr.length - 1];
        int i = Integer.parseInt(lastChar);
        Task task = taskList.get(i - 1);
        task.markAsDone();
        String message = "Nice! I've marked this task as done:\n"
                + task;
        System.out.println(wrapMessage(message));
    }

    private static String wrapMessage(String message) {
        if (message.endsWith("\n")) {
            // If the message ends with a newline, remove the newline
            message = message.substring(0, message.length() - 1);
        }

        String line = "____________________________________________________________\n";
        return line
                + message
                + "\n"
                + line;
    }
}