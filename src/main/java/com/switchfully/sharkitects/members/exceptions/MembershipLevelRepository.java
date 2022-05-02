package com.switchfully.sharkitects.members.exceptions;

import com.switchfully.sharkitects.members.MembershipLevel;
import com.switchfully.sharkitects.members.MembershipLevelName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipLevelRepository extends JpaRepository<MembershipLevel, Long> {

    MembershipLevel findByMembershipLevelName(String name);

}
