package com.example.demo;

// 2) One implementation, taking the Class<T> at construction

public class ServiceImpl<T extends BaseService, ID> implements Service<T, ID> {
    private final Class<T> type;
    private final Class<ID> idType;

    // <-- this ctor must exist
    public ServiceImpl(Class<T> type, Class<ID> idType) {
        this.type = type;
        this.idType = idType;
    }

    @Override
    public void methodA() {
        System.out.printf("Handling %s with ID type %s%n",
                type.getSimpleName(),
                idType.getSimpleName());
    }
}
