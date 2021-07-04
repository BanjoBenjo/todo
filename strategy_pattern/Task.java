
public abstract class Task {
    public String title;
    public String notes = "empty";

    public Task(String title){
        this.title = title;
    }

    public void setTitle(String newTitle){ this.title = newTitle; }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle(){ return title; }

    public void taskInfo(){
        System.out.format("Taskinfo: title: %s, notes: %s.%n%n",title ,notes);

    }
}
