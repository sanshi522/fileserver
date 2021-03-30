package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 附件
 */
@Repository
public interface AccessoryRepository extends JpaRepository<Accessory,Integer>, JpaSpecificationExecutor {
    Accessory findOneByLibraryAndFileId(Integer library,Integer fileId);
    Accessory save(Accessory access);
}
