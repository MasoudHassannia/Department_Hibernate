package dao;

import entity.Department;

import javax.persistence.EntityManager;

public class DepartmentDao extends AbstractJpaDao<Department,Integer> {

    public DepartmentDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Department> getEntityClass() {
        return Department.class;
    }
}
