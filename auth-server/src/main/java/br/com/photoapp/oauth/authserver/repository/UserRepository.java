package br.com.photoapp.oauth.authserver.repository;

import br.com.photoapp.eureka.commonservice.domain.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@UseClasspathSqlLocator
@Repository
public interface UserRepository {

    @SqlQuery
    @RegisterBeanMapper(User.class)
    Optional<User> findByEmailOrUsername(String username);
}
