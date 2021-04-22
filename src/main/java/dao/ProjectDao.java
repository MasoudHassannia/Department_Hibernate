package dao;

import entity.Project;

import javax.persistence.EntityManager;

public class ProjectDao extends AbstractJpaDao<Project,Integer> {
    public static EmployeeDao employeeDao;

    public ProjectDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Project> getEntityClass() {
        return Project.class;
    }
}
