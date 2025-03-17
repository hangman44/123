package com.hukapp.service.auth.modules.sync.dto.request.uyap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UyapSearchTrialsRequest extends UyapSearchCaseBaseRequest {
    private String baslangicTarihi;
    private String bitisTarihi;

    public UyapSearchTrialsRequest(LocalDate baslangicTarihi, LocalDate bitisTarihi) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.baslangicTarihi = baslangicTarihi.format(formatter);
        this.bitisTarihi = bitisTarihi.format(formatter);
    }

    public static class Builder {
        private LocalDate baslangicTarihi;
        private LocalDate bitisTarihi;

        public Builder baslangicTarihi(LocalDate baslangicTarihi) {
            this.baslangicTarihi = baslangicTarihi;
            return this;
        }

        public Builder bitisTarihi(LocalDate bitisTarihi) {
            this.bitisTarihi = bitisTarihi;
            return this;
        }

        public UyapSearchTrialsRequest build() {
            return new UyapSearchTrialsRequest(baslangicTarihi, bitisTarihi);
        }
    }

}
