package com.caoc.banking.cqrs.core.domain;

import com.caoc.banking.cqrs.core.events.BaseEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class AggregateRoot {
    protected String id;
    private int version = -1;
    private final List<BaseEvent> changes = new ArrayList<>();
    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public String getId() {
        return this.id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommittedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, boolean isNewEvent) {
        try {
            var method = this.getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        }catch (NoSuchMethodException e) {
            logger.warning(MessageFormat.format("El metodo apply no fue encontrado para el evento {0}", event.getClass().getName()));
        }catch (Exception e) {
            logger.severe("Error aplicando el evento al aggregate " + e);
        }finally {
            if (isNewEvent) {
                this.changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event) {
        this.applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events){
        events.forEach(event -> this.applyChange(event, false));
    }
}
