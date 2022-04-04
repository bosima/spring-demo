package cn.bossma.springdemo;

public interface PersonService {
    void insertData();
    void insertThenRollback() throws RollbackException;
    void invokeInsertThenRollback() throws RollbackException;

}
