package pers.nefedov.subscriptions.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.nefedov.subscriptions.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
