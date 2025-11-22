package lab4.domain.command;

import java.util.ArrayList;
import java.util.List;

// Invoker - manages and executes commands with undo/redo
public class CommandHistory {
    private List<Command> history = new ArrayList<>();
    private int currentPosition = -1;
    
    public void executeCommand(Command command) {
        // Remove any commands after current position (when undoing and then executing new command)
        while (history.size() > currentPosition + 1) {
            history.remove(history.size() - 1);
        }
        
        command.execute();
        history.add(command);
        currentPosition++;
        
        System.out.println("   📝 Command executed: " + command.getDescription());
        System.out.println("   📚 History size: " + history.size() + ", Position: " + (currentPosition + 1));
    }
    
    public void undo() {
        if (currentPosition < 0) {
            System.out.println("   ⚠️ Nothing to undo!");
            return;
        }
        
        Command command = history.get(currentPosition);
        command.undo();
        currentPosition--;
        
        System.out.println("   ↩️ Undone: " + command.getDescription());
        System.out.println("   📚 Position: " + (currentPosition + 1));
    }
    
    public void redo() {
        if (currentPosition >= history.size() - 1) {
            System.out.println("   ⚠️ Nothing to redo!");
            return;
        }
        
        currentPosition++;
        Command command = history.get(currentPosition);
        command.execute();
        
        System.out.println("   ↪️ Redone: " + command.getDescription());
        System.out.println("   📚 Position: " + (currentPosition + 1));
    }
    
    public void showHistory() {
        System.out.println("\n📋 Command History:");
        if (history.isEmpty()) {
            System.out.println("   (empty)");
            return;
        }
        
        for (int i = 0; i < history.size(); i++) {
            String marker = (i == currentPosition) ? " 👉 " : "    ";
            System.out.println(marker + (i + 1) + ". " + history.get(i).getDescription());
        }
    }
}
