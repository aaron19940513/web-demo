package com.sam.demo.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Component
//@ConfigurationProperties(prefix = "spring.jpa.hibernate")
public class HibernateConfig {
    private String batchValueSize;
    private String orderInserts;
    private String orderUpdates;
    private String batchVersionData;

    /**
     * Gets batch value size.
     *
     * @return the batch value size
     */
    public String getBatchValueSize() {
        return batchValueSize;
    }

    /**
     * Sets batch value size.
     *
     * @param batchValueSize the batch value size
     */
    public void setBatchValueSize(String batchValueSize) {
        this.batchValueSize = batchValueSize;
    }

    /**
     * Gets order inserts.
     *
     * @return the order inserts
     */
    public String getOrderInserts() {
        return orderInserts;
    }

    /**
     * Sets order inserts.
     *
     * @param orderInserts the order inserts
     */
    public void setOrderInserts(String orderInserts) {
        this.orderInserts = orderInserts;
    }

    /**
     * Gets order updates.
     *
     * @return the order updates
     */
    public String getOrderUpdates() {
        return orderUpdates;
    }

    /**
     * Sets order updates.
     *
     * @param orderUpdates the order updates
     */
    public void setOrderUpdates(String orderUpdates) {
        this.orderUpdates = orderUpdates;
    }

    /**
     * Gets batch version data.
     *
     * @return the batch version data
     */
    public String getBatchVersionData() {
        return batchVersionData;
    }

    /**
     * Sets batch version data.
     *
     * @param batchVersionData the batch version data
     */
    public void setBatchVersionData(String batchVersionData) {
        this.batchVersionData = batchVersionData;
    }
}
