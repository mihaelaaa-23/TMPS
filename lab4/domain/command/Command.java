package lab4.domain.command;

// Command interface - all commands must implement this
public interface Command {
    void execute();
    void undo();
    String getDescription();
}
