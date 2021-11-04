package sample;

public class Project {

    int ID;
    String Project;
    String Zone;
    String Scope;

    public Project(int ID, String project, String zone, String scope) {
        this.ID = ID;
        Project = project;
        Zone = zone;
        Scope = scope;
    }

    public Project() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProject() {
        return Project;
    }

    public void setProject(String project) {
        Project = project;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getScope() {
        return Scope;
    }

    public void setScope(String scope) {
        Scope = scope;
    }
}