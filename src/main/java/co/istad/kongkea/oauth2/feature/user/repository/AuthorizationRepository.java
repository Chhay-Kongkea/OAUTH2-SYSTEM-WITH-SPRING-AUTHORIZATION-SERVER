package co.istad.kongkea.oauth2.feature.user.repository;

import co.istad.kongkea.oauth2.feature.user.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorizationRepository
        extends JpaRepository<Authorization, String> {

    Optional<Authorization> findByState(String state);

    Optional<Authorization> findByAuthorizationCodeValue(String value);

    Optional<Authorization> findByAccessTokenValue(String value);

    Optional<Authorization> findByRefreshTokenValue(String value);

    Optional<Authorization> findByOidcIdTokenValue(String value);

    Optional<Authorization> findByUserCodeValue(String value);

    Optional<Authorization> findByDeviceCodeValue(String value);

    @Query("select a from Authorization a where a.state = :value "
            + "or a.authorizationCodeValue = :value "
            + "or a.accessTokenValue = :value "
            + "or a.refreshTokenValue = :value "
            + "or a.oidcIdTokenValue = :value "
            + "or a.userCodeValue = :value "
            + "or a.deviceCodeValue = :value")
    Optional<Authorization> findByAnyToken(@Param("value") String value);
}
