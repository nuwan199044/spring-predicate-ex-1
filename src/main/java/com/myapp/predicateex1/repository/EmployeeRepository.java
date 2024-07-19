package com.myapp.predicateex1.repository;

import com.myapp.predicateex1.dto.SearchRequest;
import com.myapp.predicateex1.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {

    private final EntityManager em;

    public List<Employee> findAllBySimpleQuery(String firstname, String lastname, String email) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        //select * from employee
        Root<Employee> root = criteriaQuery.from(Employee.class);

        // prepare where clouse
        // where firstname like '%test%'
        Predicate firstnamePredicate = criteriaBuilder.like(root.get("firstname"), "%" + firstname + "%");

        Predicate lastnamePredicate = criteriaBuilder.like(root.get("lastname"), "%" + lastname + "%");

        Predicate emailPredicate = criteriaBuilder.like(root.get("email"), "%" + email + "%");

        Predicate firstnameOrLastnamePredicate = criteriaBuilder.or(firstnamePredicate, lastnamePredicate);

        // final query => select * from employee where firstname like '' or lastname like '' and email like ''
        criteriaQuery.where(
                criteriaBuilder.and(firstnameOrLastnamePredicate, emailPredicate)
        );
        TypedQuery<Employee> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Employee> findAllBySimpleQuery(SearchRequest request) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        List<Predicate> predicates = new ArrayList<>();

        //select * from employee
        Root<Employee> root = criteriaQuery.from(Employee.class);

        if(request.getFirstname() != null) {
            Predicate firstnamePredicate  = criteriaBuilder.like(root.get("firstname"), "%" + request.getFirstname() + "%");
            predicates.add(firstnamePredicate);
        }

        if(request.getLastname() != null) {
            Predicate lastnamePredicate  = criteriaBuilder.like(root.get("lastname"), "%" + request.getLastname() + "%");
            predicates.add(lastnamePredicate);
        }

        if(request.getFirstname() != null) {
            Predicate emailPredicate  = criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%");
            predicates.add(emailPredicate);
        }

        // prepare where clouse
        // where firstname like '%test%'


        // final query => select * from employee where firstname like '' or lastname like '' and email like ''
        criteriaQuery.where(
                criteriaBuilder.or(predicates.toArray(new Predicate[0]))
        );
        TypedQuery<Employee> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
