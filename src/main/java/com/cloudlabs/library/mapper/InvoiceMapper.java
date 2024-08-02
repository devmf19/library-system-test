package com.cloudlabs.library.mapper;

import com.cloudlabs.library.dto.request.InvoiceRequestDto;
import com.cloudlabs.library.dto.response.InvoiceItemResponseDto;
import com.cloudlabs.library.dto.response.InvoiceResponseDto;
import com.cloudlabs.library.model.Invoice;
import com.cloudlabs.library.model.InvoiceItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookMapperImpl.class, MemberMapper.class, UserMapper.class})
public interface InvoiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Mapping(target = "user", ignore = true)
    Invoice toEntity(InvoiceRequestDto invoiceRequestDto);

    @Mapping(target = "books", ignore = true)
    InvoiceResponseDto toResponse(Invoice invoice);

    List<InvoiceResponseDto> toResponseList(List<Invoice> invoice);


    InvoiceItemResponseDto itemToResponse(InvoiceItem invoiceItem);

    List<InvoiceItemResponseDto> itemToResponseList(List<InvoiceItem> invoiceItems);
}
