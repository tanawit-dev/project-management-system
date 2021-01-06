package com.example.projectmanagementsystem.dto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class TaskDto {

	private Long id;

	@NotBlank
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime beginAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime finishAt;

	private String detail;

	private Long assignedId;

	private Long projectId;

	@Getter(AccessLevel.NONE)
	private boolean isCompleted;

	@Getter(AccessLevel.NONE)
	private double md;

	public boolean isCompleted() {
		return beginAt != null && finishAt != null && detail != null && !detail.isEmpty();
	}

	public double getMd() {
		md = calculateMD(beginAt, finishAt);
		return md;
	}

	private double calculateMD(LocalDateTime beginAt, LocalDateTime finishAt) {
		double mh = 0;
		if (beginAt != null && finishAt != null) {
			LocalDateTime current = beginAt;
			while (current.toLocalDate().isBefore(finishAt.toLocalDate())) {
				current = current.plusDays(1);
				DayOfWeek dayOfWeek = current.getDayOfWeek();
				if (dayOfWeek.name().equals(DayOfWeek.SATURDAY.name())
						|| dayOfWeek.name().equals(DayOfWeek.SUNDAY.name())) {
					continue;
				}
				mh += 8;
			}

			LocalTime startWorkTime = LocalTime.of(9, 0);
			LocalTime endWorkTime = LocalTime.of(18, 00);
			LocalTime beginTime = beginAt.toLocalTime();
			LocalTime finishTime = finishAt.toLocalTime();

			if (beginTime.isAfter(startWorkTime)) {
				if (beginTime.isAfter(endWorkTime)) {
					mh += 8;
				} else {
					double workHour = ChronoUnit.HOURS.between(startWorkTime, beginTime);
					mh += workHour >= 4 ? workHour - 1 : workHour;
				}
			}

			if (finishTime.isAfter(startWorkTime)) {
				if (finishTime.isAfter(endWorkTime)) {
					mh += 8;
				} else {
					double workHour = ChronoUnit.HOURS.between(startWorkTime, finishTime);
					mh += workHour >= 4 ? workHour - 1 : workHour;
				}
			}
		}

		return mh == 0 ? 0 : mh / 8;
	}

}
