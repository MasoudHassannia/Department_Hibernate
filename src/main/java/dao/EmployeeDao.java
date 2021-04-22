package dao;

import entity.Employee;
import entity.Project;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class EmployeeDao extends AbstractJpaDao<Employee,Integer> {

    public EmployeeDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Employee> getEntityClass() {
        return Employee.class;
    }

    public Employee loadByEmail(String email) {
        TypedQuery<Employee> typedQuery = super.entityManager
                .createQuery("select emp from Employee emp where emp.email = :email",Employee.class);

        typedQuery.setParameter("email",email);
        return typedQuery.getSingleResult();
    }

    //new code
    public List<Employee> loadByCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();

        criteriaQuery.select(criteriaQuery.from(Employee.class));

        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Employee> loadByEmailCriteria(String email){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> fromEmployee = criteriaQuery.from(Employee.class);
        criteriaQuery.select(fromEmployee).where(criteriaBuilder.equal(fromEmployee.get("email"),email));

        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Employee> loadByEmailCriteria1(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> fromEmployee = criteriaQuery.from(Employee.class);

        Predicate predicate = criteriaBuilder.and
                (criteriaBuilder.like(fromEmployee.get("email"),"%com"),
                criteriaBuilder.isNotNull(fromEmployee.get("lastName")));

        predicate = criteriaBuilder.or(predicate,criteriaBuilder.equal(fromEmployee.get("lastName"),"hassani"));

        criteriaQuery.select(fromEmployee).where(predicate).orderBy(criteriaBuilder.asc(fromEmployee.get("lastName")));

        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Employee> joinByCriteria(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> fromEmployee = criteriaQuery.from(Employee.class);
        criteriaQuery.select(fromEmployee).where(criteriaBuilder.equal(fromEmployee.get("department").get("name"),"IT"));

        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Employee> joinByCriteria1(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> fromEmployee = criteriaQuery.from(Employee.class);
        Join<Employee, Project> empProJoin = fromEmployee.join("projects");
        criteriaQuery.select(fromEmployee).where(criteriaBuilder.equal(empProJoin.get("title"),"projectB"));

        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
