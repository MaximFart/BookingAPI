package com.example.dto;

public class BookingDto {

    private Long id;
    private TourDto tourDto;
    private UserDto userDto;
    private GuideDto guideDto;

    public BookingDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TourDto getTourDto() {
        return tourDto;
    }

    public void setTourDto(TourDto tourDto) {
        this.tourDto = tourDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public GuideDto getGuideDto() {
        return guideDto;
    }

    public void setGuideDto(GuideDto guideDto) {
        this.guideDto = guideDto;
    }
}
