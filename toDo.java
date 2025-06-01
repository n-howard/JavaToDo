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
    public void add(String task, Double priority){
        this.toDoList.put(priority, task);
    }

    /**
     * Allows the user to add a task.
     */
    public void addTask(){
        System.out.println("Enter the name of the task you want to add");
        Scanner scan = new Scanner(System.in);
        String nex = scan.nextLine();
        System.out.println("Enter the priority of the task.");
        Double nexDo = scan.nextDouble();
        scan.close();
        this.add(nex, nexDo);
    }

    /**
     * Allows the user to edit the priority of an existing task.
     */
    public void editPriority(){
        System.out.println("What is the current priority of the task you want to update?");
        Scanner scan = new Scanner(System.in);
        Double oldPrio = scan.nextDouble();
        if(this.toDoList.containsKey(oldPrio)==false){
            System.out.println("A task with that priority was not found.");
            scan.close();
            return;
        }
        String task = this.toDoList.get(oldPrio);
        System.out.println("What is the new priority of the task you want to update?");
        double prio = scan.nextDouble();
        scan.close();
        boolean con = this.toDoList.containsKey(prio);
        if(con==true){
            while(con == true){
                prio = prio + .1;
            }
        }
        this.toDoList.remove(oldPrio);
        this.add(task, prio);
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
    public String remove(double key){
        String task = this.toDoList.get(key);
        this.toDoList.remove(key);
        return task;
    }

    /**
     * Allows the user to remove a task.
     */
    public void removeTask(){
        System.out.println("Enter the priority of the task you want to remove.");
        Scanner scan = new Scanner(System.in);
        double key = scan.nextDouble();
        scan.close();
        if(this.toDoList.containsKey(key)==false){
            System.out.println("A task with that priority was not found.");
            return;
        }
        this.remove(key);
    }

    /**
     * Allows a user to complete a task.
     * @return The completed task.
     */
    public String complete(){
        System.out.println("Enter the priority of the completed task");
        Scanner scan = new Scanner(System.in);
        double key = scan.nextDouble();
        scan.close();
        if(this.toDoList.containsKey(key)==false){
            System.out.println("A task with that priority was not found.");
            scan.close();
            return null;
        }
        String task = this.toDoList.get(key);
        this.completedTasks.add("x " + task);
        return this.remove(key);
    }

    /**
     * Retrieves the list of completed tasks.
     * @return the ArrayList of completed tasks.
     */
    public ArrayList<String> getCompletedTasks(){
        return this.completedTasks;
    }

    /**
     * Allows users to select the action to take.
     */
    public void start(){
        Scanner response = new Scanner(System.in);
        int input = 0;
        while(input!=4){
            System.out.println("Commands: ");
            System.out.println("1. Add");
            System.out.println("2. List");
            System.out.println("3. Remove");
            System.out.println("4. Quit");
            input = response.nextInt();
             if(input == 1) {
                addTask();
            } else if(input == 2) {
                print();
            } else if(input == 3) {
                removeTask();
            } 
            else{
                System.out.println("Sorry, don't know how to handle the number " + input);
                continue;
            }
        }
        response.close();
        return;
    }

    /**
     * Adds the todo list to a file. Inspired by SenorSeniorDevSr on Reddit.
     */
    public void writeToFile() {
        try(PrintStream fileOut = new PrintStream(new File("data.txt"))) {
            for(double keys : this.getTasks().keySet()) {     
                String task = this.toDoList.get(keys);
                fileOut.println(keys + "\t" + task);
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
                String[] array = sc.nextLine().split("\t");
                double key = Double.valueOf(array[0]);
                newTodoList.add(array[1], key);
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found. Please enter a valid file name.");
            System.exit(1);
        }
        this.toDoList = newTodoList.toDoList;
    }

    public static void main(String[] inputs){
        toDo todo = new toDo();
        todo.readFromFile();
        todo.start();
        todo.writeToFile();
    }

}