package com.switchfully.sharkitects.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Member save(Member member);

    List<Member> findAllByFirstNameAndLastName(String firstName, String lastName);
}
