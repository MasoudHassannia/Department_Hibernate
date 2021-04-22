import dao.DepartmentDao;
import dao.EmployeeDao;
import dao.ProjectDao;
import entity.Department;
import entity.Employee;
import entity.Project;
import util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;


public class App {
    private static DepartmentDao departmentDao;
    private static ProjectDao projectDao;
    private static EmployeeDao employeeDao;


    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        initializeDao(entityManager);

        entityManager.getTransaction().begin();
        initializeDate();

//        Department department = createDepartment("Test");
//        Integer depID = department.getID();
//        System.out.println("depID: " +depID);
//
//        departmentDao.delete(department);

        entityManager.getTransaction().commit();

        employeeDao.loadByCriteria();
        employeeDao.loadByEmailCriteria("J@hamil.com");
        employeeDao.loadByEmailCriteria1();
        employeeDao.joinByCriteria();

//        Employee employee = employeeDao.load(CHECK_EMP_ID);
//        System.out.println(employee.getProjectSet().size());
//        System.out.println(employee.getDepartment().getName());

//        if(departmentDao.load(5) == null){
//            System.out.println("depart test is deleted!");
//        }


        entityManager.close();
        JPAUtil.shutdown();
    }

    private static void initializeDate() {

            Department departmentIt = createDepartment("It");
            Project projectA = createProject("A");
            Project projectB = createProject("B");

            Employee employee = new Employee();
            employee.setFirstName("hassan");
            employee.setLastName("hassani");
            employee.setEmail("J@hamil.com");
            employee.setDepartment(departmentIt);
            Set<Project> projects = new HashSet<>();
            projects.add(projectA);
            projects.add(projectB);
            employee.setProjectSet(projects);
            employeeDao.save(employee);


    }

    private static Department createDepartment(String title) {
        Department department = new Department();
        department.setName(title);
        departmentDao.save(department);
        return department;
    }

    private static Project createProject(String title) {
        Project project = new Project();
        project.setTitle(title);
        projectDao.save(project);
        return project;
    }

    private static void initializeDao(EntityManager entityManager){
        employeeDao = new EmployeeDao(entityManager);
        departmentDao = new DepartmentDao(entityManager);
        projectDao = new ProjectDao(entityManager);

    }

    private static void deleteAllData(EntityManager entityManager) {
        entityManager.createNamedQuery("delete form employee").executeUpdate();
    }


}
