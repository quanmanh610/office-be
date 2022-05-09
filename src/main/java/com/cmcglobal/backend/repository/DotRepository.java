package com.cmcglobal.backend.repository;

import com.cmcglobal.backend.entity.Dot;
import com.cmcglobal.backend.entity.Floor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface DotRepository extends JpaRepository<Dot, Integer> {
    List<Dot> findAllByMemberInAndStatusIs(List<String> usernames, String status);

    @Query(value = "SELECT * FROM dot" +
            " WHERE (floor_id = :floorId)" +
            " AND (:date IS NULL OR (from_date <= :date AND to_date >= :date))" +
            " AND (:member IS NULL OR member = :member)" +
            " AND (:#{#userIdsInGroup.size} < 1 OR owner IN (:userIdsInGroup))", nativeQuery = true)
    List<Dot> findDotsByConditions(@Param("floorId") Integer floorId, @Param("userIdsInGroup") List<String> userIdInGroup, @Param("date") String date, @Param("member") String member);

    @Query(value = "SELECT * FROM office.dot" +
            " WHERE (:#{#floorIds.size()} < 1 OR floor_id IN (:floorIds))" +
            " AND (:#{#status.size()} < 1 OR status IN (:status))" +
            " AND (:#{#userIdsInGroup.size()} < 1 OR owner IN (:userIdsInGroup))" +
            " AND (:member IS NULL OR member = :member)" +
            " AND (:date IS NULL OR (from_date <= :date AND to_date >= :date))", nativeQuery = true)
    Page<Dot> findDots(@Param("floorIds") List<Integer> floorIds, @Param("userIdsInGroup") List<String> userIdsInGroup, @Param("member") String member, @Param("date") String date, @Param("status") List<String> status, Pageable paging);

    @Query(value = "SELECT * FROM office.dot" +
            " WHERE (:#{#floorIds.size()} < 1 OR floor_id IN (:floorIds))" +
            " AND (:#{#status.size()} < 1 OR status IN (:status))" +
            " AND (:#{#userIdsInGroup.size()} < 1 OR owner IN (:userIdsInGroup))" +
            " AND (:member IS NULL OR member = :member)" +
            " AND ((:fromDate IS NULL OR :toDate IS NULL) OR (( from_date >= :fromDate AND from_date <= :toDate) OR (from_date <= :fromDate AND to_date >= :toDate) OR (to_date >= :fromDate AND to_date <= :toDate)))", nativeQuery = true)
    Page<Dot> findDotsReport(@Param("floorIds") List<Integer> floorIds, @Param("userIdsInGroup") List<String> userIdsInGroup, @Param("member") String member, @Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("status") List<String> status, Pageable paging);

    @Query(value = "SELECT * FROM office.dot" +
            " WHERE (:#{#floorIds.size()} < 1 OR floor_id IN (:floorIds))" +
            " AND (:#{#status.size()} < 1 OR status IN (:status))" +
            " AND (:#{#userIdsInGroup.size()} < 1 OR owner IN (:userIdsInGroup))" +
            " AND (:member IS NULL OR member = :member)" +
            " AND ((:fromDate IS NULL OR :toDate IS NULL) OR (( from_date >= :fromDate AND from_date <= :toDate) OR (from_date <= :fromDate AND to_date >= :toDate) OR (to_date >= :fromDate AND to_date <= :toDate)))", nativeQuery = true)
    List<Dot> findDotsReport(@Param("floorIds") List<Integer> floorIds, @Param("userIdsInGroup") List<String> userIdsInGroup, @Param("member") String member, @Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("status") List<String> status);

    Integer countDotsByFloorAndType(Floor floor, String type);

    @Modifying
    @Query(value = "UPDATE dot SET member = :member, status = :status WHERE id = :id", nativeQuery = true)
    void updateUserAndStatus(@Param("member") String member, @Param("status") String status, @Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE dot SET owner = :username, member = :username, status = :status, from_date = :fromDate, to_date = :toDate WHERE id IN (:dotIds)", nativeQuery = true)
    void updateOwnerUserAndStatus(@Param("username") String owner, @Param("status") String status, @Param("dotIds") List<Integer> dotIds, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Modifying
    @Query(value = "UPDATE dot SET member = :username, status = :status, from_date = :fromDate, to_date = :toDate WHERE id IN (:dotIds)", nativeQuery = true)
    void updateUserAndStatus(@Param("username") String memberUsername, @Param("status") String status, @Param("dotIds") List<Integer> dotIds, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Modifying
    @Query(value = "UPDATE dot SET member = null, owner = null,from_date = null, to_date = null, status = 'available' WHERE id IN (:dotId)", nativeQuery = true)
    void resetDots(@Param("dotId") List<Integer> dotIds);

    @Modifying
    @Query(value = "UPDATE dot SET member = :username, from_date = :fromDate, to_date = :toDate, status = :status WHERE id IN (:dotIds)", nativeQuery = true)
    void updateDateRangeAndStatus(@Param("username") String memberUsername, @Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("status") String status, @Param("dotIds") List<Integer> dotIds);

    @Modifying
    @Query(value = "UPDATE dot SET status = :status WHERE id IN (:dotIds)", nativeQuery = true)
    void updateStatus(@Param("status") String status, @Param("dotIds") List<Integer> dotIds);

    @Query(value = "SELECT id FROM dot WHERE to_date < :now", nativeQuery = true)
    List<Integer> findAllByToDateAfter(LocalDate now);

    @Modifying
    @Query(value = "UPDATE dot SET status = 'available', from_date = null, to_date = null, member = null, owner_id = null where id in (:dotIds)", nativeQuery = true)
    void turnExpiredDotToAvailable(List<Integer> dotIds);

    @Modifying
    @Query(value = "UPDATE dot SET from_date = :fromDate, to_date = :toDate WHERE id IN (:dotIds)", nativeQuery = true)
    void updateDateRange(@Param("dotIds") List<Integer> dotIds, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "SELECT * FROM dot WHERE from_date >= :fromDate AND to_date <= :toDate", nativeQuery = true)
    List<Dot> findAllByMonth(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    boolean existsByMember(String username);

    boolean existsByMemberAndStatus(String username, String status);
}
