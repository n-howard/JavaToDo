import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * A public class that creates a ToDo List in the terminal/
 * 
 * @author Nat
 */
public class toDo{

    /**
     * The todo list.
     */
    private TreeMap<Double, String> toDoList;

    /**
     * The list of completed tasks.
     */
    private ArrayList<String> completedTasks;

    /**
     * The public constructor.
     */
    public toDo(){
        this.toDoList = new TreeMap<Double, String>();
        this.completedTasks = new ArrayList<String>();
    }
    /**
     * Retrieves the ToDo list.
     * @return the todo list ArrayList
     */
    public TreeMap<Double, String> getTasks(){
        return this.toDoList;
    }

    /**
     * Adds a task.
     * @param task The task to add.
     */
    protected void add(String task, Double priority){
        this.toDoList.put(priority, task);
    }

    /**
     * Allows the user to add a task.
     */
    public void addTask(Scanner scan){
        System.out.println("Enter the name of the task you want to add");
        String nex = scan.nextLine();
        System.out.println("Enter the priority of the task.");
        try{
            Double nexDo = Double.valueOf(scan.nextLine());
            boolean con = this.toDoList.containsKey(nexDo);
            if(con==true){
                while(con == true){
                    nexDo = nexDo + .1;
                    con = this.toDoList.containsKey(nexDo);
                }
            }
            this.add(nex, nexDo);
        } catch(NumberFormatException e){
            System.out.println("Please enter an integer or double.");
            return;
        }

    }

    /**
     * Allows the user to edit the priority of an existing task.
     */
    public void editPriority(Scanner scan){
        System.out.println("What is the current priority of the task you want to update?");
        try{
            Double oldPrio = Double.valueOf(scan.nextLine());
            if(this.toDoList.containsKey(oldPrio)==false){
                System.out.println("A task with that priority was not found.");
                return;
            }
            String task = this.toDoList.get(oldPrio);
            System.out.println("What is the new priority of the task you want to update?");
            double prio = Double.valueOf(scan.nextLine());
            boolean con = this.toDoList.containsKey(prio);
            if(con==true){
                while(con == true){
                    prio = prio + .1;
                    con = this.toDoList.containsKey(prio);
                }
            }
            this.toDoList.remove(oldPrio);
            this.add(task, prio);
        } catch(NumberFormatException e){
            System.out.println("Please enter an integer or double.");
            return;
        }
    }

    /**
     * Prints out all of the tasks with their priority.
     */
    public void print(){
        for(double key : this.toDoList.keySet()){
            String val = this.toDoList.get(key);
            System.out.println(key + ". " + val);
        }
    }

    /**
     * Removes a task.
     * @param key The priority of the task to remove.
     * @return The value of the removed task.
     */
    protected String remove(double key){
        String task = this.toDoList.get(key);
        this.toDoList.remove(key);
        return task;
    }

    /**
     * Allows the user to remove a task.
     */
    public void removeTask(Scanner scan){
        System.out.println("Enter the priority of the task you want to remove.");
        try{
            double key = Double.valueOf(scan.nextLine());
            if(this.toDoList.containsKey(key)==false){
                System.out.println("A task with that priority was not found.");
                return;
            }
            this.remove(key);
        } catch(NumberFormatException e){
            System.out.println("Please enter an integer or double.");
            return;
        }
    }

    /**
     * Allows a user to complete a task.
     * @return The completed task.
     */
    public String complete(Scanner scan){
        System.out.println("Enter the priority of the completed task");
        try{
            double key = Double.valueOf(scan.nextLine());
            if(this.toDoList.containsKey(key)==false){
                System.out.println("A task with that priority was not found.");
                return null;
            }
            String task = this.toDoList.get(key);
            this.completedTasks.add("x - " + task);
            return this.remove(key);
        } catch(NumberFormatException e){
            System.out.println("Please enter an integer or double.");
            return null;
        }

    }

    /**
     * Retrieves the list of completed tasks.
     * @return the ArrayList of completed tasks.
     */
    public ArrayList<String> getCompletedTasks(){
        return this.completedTasks;
    }

    public void edit(Scanner scan){
        System.out.println("Would you like to (1) Edit a priority or (2) Edit a task?");
        try{
            int resp = Integer.valueOf(scan.nextLine());
            if(resp == 1){
                this.editPriority(scan);
            } else if(resp == 2){
                this.editTask(scan);
            } else{
                System.out.println("I can't handle the value " + resp + ". Please enter 1 or 2.");
            } 
        } catch(NumberFormatException e){
            System.out.println("Please enter an integer 1 or 2.");
            return;
        }
        
    }
    
    public void editTask(Scanner scan){
        System.out.println("What is the priority of the task you want to edit?");
        try{
            double key = Double.valueOf(scan.nextLine());
            if(this.toDoList.containsKey(key)==false){
                System.out.println("A task with that priority was not found.");
                return;
            }
            System.out.println("What is the new title of the task?");
            String task = scan.nextLine();
            this.toDoList.replace(key, task);
        } catch(NumberFormatException e){
            System.out.println("Please enter an integer or double.");
            return;
        }
    }

    /**
     * Allows users to select the action to take.
     */
    public void start(Scanner response){
        String input = "Y";
        while(input.equals("Z") == false){
            System.out.println("Commands: ");
            System.out.println("A. Add");
            System.out.println("B. List");
            System.out.println("C. Remove");
            System.out.println("D. Edit");
            System.out.println("E. Complete");
            System.out.println("Z. Quit");
            input = response.nextLine();
            
             if(input.equals("A")) {
                this.addTask(response);
            } else if(input.equals("B")) {
                this.print();
            } else if(input.equals("C")) {
                this.removeTask(response);
            } else if(input.equals("D")){
                this.edit(response);
            } else if(input.equals("E")){
                this.complete(response);
            } else if(input.equals("Z")){
                break;
            }
            else{
                System.out.println("I can't handle the character " + input + ". Please enter a character A through E or Z.");
                continue;
            }
        }
        return;
    }

    /**
     * Adds the todo list to a file. Inspired by SenorSeniorDevSr on Reddit.
     */
    public void writeToFile() {
        try(PrintStream fileOut = new PrintStream(new File("todo.txt"))) {
            for(double keys : this.getTasks().keySet()) {     
                String task = this.toDoList.get(keys);
                fileOut.println(keys + " - " + task);
            }
            for(String comps : this.getCompletedTasks()){
                fileOut.println(comps);
            }
        } catch(IOException ioe) {
            System.err.println("Error writing to file: " + ioe);
        }
    }  



    /**
     * Retrieves the todo list from a file. Inspired by SenorSeniorDevSr on Reddit.
     * 
     * **/
    public void readFromFile() {
        toDo newTodoList = new toDo();
        try(Scanner sc = new Scanner(new File("todo.txt"))) {
            while(sc.hasNext()) {
                String[] array = sc.nextLine().split(" - ");
                if(array[0].contains("x")){
                    newTodoList.getCompletedTasks().add(array[0] + " - " + array[1]);
                    continue;
                }
                else{
                    double key = Double.valueOf(array[0]);
                    newTodoList.add(array[1], key);  
                }
                
            }
            sc.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found. Please enter a valid file name.");
            System.exit(1);
        }
        this.toDoList = newTodoList.toDoList;
        this.completedTasks = newTodoList.completedTasks;
    }

    public static void main(String[] inputs){
        Scanner scanner = new Scanner(System.in);
        toDo todo = new toDo();
        todo.readFromFile();
        todo.start(scanner);
        scanner.close();
        todo.writeToFile();
    }

}