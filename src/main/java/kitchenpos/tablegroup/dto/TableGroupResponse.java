package kitchenpos.tablegroup.dto;

import kitchenpos.tablegroup.domain.TableGroup;

import java.time.LocalDateTime;

public class TableGroupResponse {
    private Long id;
    private LocalDateTime createdDate;

    public TableGroupResponse() {
    }

    public TableGroupResponse(Long id, LocalDateTime createdDate) {
        this.id = id;
        this.createdDate = createdDate;
    }

    public static TableGroupResponse of(TableGroup tableGroup) {
        return new TableGroupResponse(
                tableGroup.getId(),
                tableGroup.getCreatedDate()
        );
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
