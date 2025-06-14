package com.socize.pages.fileserver.admin.shared.adminpage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.socize.pages.fileserver.admin.shared.adminpage.adminpagestatus.AdminPageStatus;

public class DefaultAdminPageStatusManager implements AdminPageStatusManager {
    private AdminPageStatus status;
    private final List<Consumer<AdminPageStatus>> observers;

    private DefaultAdminPageStatusManager() {
        this.observers = new ArrayList<>();
    }

    private static class SingletonInstanceHolder {
        private static final DefaultAdminPageStatusManager INSTANCE = new DefaultAdminPageStatusManager();
    }

    public static DefaultAdminPageStatusManager getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public void subscribe(Consumer<AdminPageStatus> observer) {
        
        synchronized(observers) {
            observers.add(observer);
        }

    }

    @Override
    public void unsubscribe(Consumer<AdminPageStatus> observer) {
        
        synchronized(observers) {
            observers.remove(observer);
        }

    }

    @Override
    public synchronized void setPage(AdminPageStatus adminPageStatus) {
        this.status = adminPageStatus;
        notifyObservers();
    }
    
    private void notifyObservers() {

        synchronized(observers) {
            for(Consumer<AdminPageStatus> observer : observers) {
                observer.accept(status);
            }
        }
    }
}
