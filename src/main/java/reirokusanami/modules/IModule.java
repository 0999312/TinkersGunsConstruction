package reirokusanami.modules;

import java.util.LinkedHashSet;
import java.util.Set;

public interface IModule {
    public static final Set<IModule> MODULES = new LinkedHashSet<>();

    default void Initialization(){}
    default void secondInitialization(){}
    default void thirdInitialization(){}
}
