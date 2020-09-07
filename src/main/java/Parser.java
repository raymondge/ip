import java.time.LocalDate;
public class Parser {


    public String uiResponse(TaskList tasks, UI ui,String uiInput) {

        String response = "";
        String textMessage = uiInput;

            try {
                String[] keyword = textMessage.split(" ");
                switch (keyword[0]) {

                    case "bye": {
                        response += "Bye. Hope to see you again soon!";
                    }

                    case "find": {

                        response += tasks.findTask(keyword[1]);
                    }

                    case "delete": {
                        int index = Integer.parseInt(keyword[1]) - 1;
                        response += tasks.deleteTask(index);
                    }

                    case "todo": {

                        if (keyword.length == 1) {
                            throw new InvalidTodoException();
                        }
                        Todo newTodo = new Todo(textMessage.substring(5), false);
                        response += tasks.addTask(newTodo);
                    }


                    case "deadline": {

                        if (keyword.length == 1) {
                            throw new InvalidDeadlineException();
                        }
                        try {
                            String[] tempString = textMessage.substring(9).split(" /by ");
                            Deadline newDeadline = new Deadline(tempString[0], false, LocalDate.parse(tempString[1]));
                            response += tasks.addTask(newDeadline);
                        } catch (Exception e) {
                            throw new InvalidFormatException();
                        }

                    }

                    case "event": {

                        if (keyword.length == 1) {
                            throw new InvalidEventException();
                        }
                        try {
                            String[] tempString = textMessage.substring(7).split(" /at ");
                            Event newEvent = new Event(tempString[0], false, LocalDate.parse(tempString[1]));
                            response += tasks.addTask(newEvent);
                        } catch (Exception e) {
                            throw new InvalidFormatException();
                        }

                    }

                    case "list": {
                        response += tasks.showList();
                    }

                    case "done": {
                        int index = Integer.parseInt(keyword[1]) - 1;
                        tasks.getTasks().get(index).markDone();
                        response += "Nice I've marked this tasks as done";
                        response += tasks.getTasks().get(index);
                    }

                    default:
                        throw new InvalidInputException();
                }

            }
            catch (DukeException e) {
                response += e.getMessage();
            }

        return response;
    }

}
