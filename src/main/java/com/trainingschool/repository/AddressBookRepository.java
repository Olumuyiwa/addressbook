package com.trainingschool.repository;

import com.trainingschool.entity.AddressBookEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AddressBookRepository extends CrudRepository<AddressBookEntity, Long> {

    List<AddressBookEntity> findByLastName(String lastName);

    List<AddressBookEntity> findByFirstName(String firstName);

    List<AddressBookEntity> findByEmailAddress(String emailAddress);

    List<AddressBookEntity> findByPhoneNumber(String phoneNumber);

    Optional<AddressBookEntity> findById(Long id);

    @Override
    <S extends AddressBookEntity> S save(S entity);

    @Override
    <S extends AddressBookEntity> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    boolean existsById(Long aLong);


    @Override
    long count();

    @Override
    void deleteById(Long id);

    @Override
    void delete(AddressBookEntity addressBookEntity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends AddressBookEntity> entities);

    @Override
    void deleteAll();

    @Override
    Iterable<AddressBookEntity> findAllById(Iterable<Long> id);

    @Override
    Iterable<AddressBookEntity> findAll();
}
