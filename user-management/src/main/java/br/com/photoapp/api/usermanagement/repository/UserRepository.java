package br.com.photoapp.api.usermanagement.repository;

import br.com.photoapp.api.usermanagement.domain.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@UseClasspathSqlLocator
@Repository
public interface UserRepository {

    @SqlUpdate
    @GetGeneratedKeys
    Long createUser(@BindBean User user);

    @SqlQuery
    @RegisterBeanMapper(User.class)
    Optional<User> findById(Long id);
}
