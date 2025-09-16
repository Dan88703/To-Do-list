package Nad.Sonork.todo;

public class Goals {
    private int id;
    private String title;
    private String description;
    private String deadline;

    public Goals(int id, String title,String description, String deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }
}
