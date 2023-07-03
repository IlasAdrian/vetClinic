package com.sda.vetClinic.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AppointmentDto {
    private String id;
    private String petId;
    private String petName;
    private String type;
    private String description;
    private String status;
    private String vetId;
    private String date;
}
