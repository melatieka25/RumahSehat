package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.*;
import TA_C_SHA_90.RumahSehatWeb.repository.ResepDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResepServiceImpl implements ResepService {
    @Autowired
    ResepDb resepDb;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ObatService obatService;

    @Override
    public void addResep(ResepModel resep) {
        LocalDateTime now = LocalDateTime.now();
        resep.setCreatedAt(now);
        resep.setIsDone(false);
        resepDb.save(resep);
    }

    @Override
    public List<ResepModel> getListResep() {
        return resepDb.findAll();
    }

    @Override
    public ResepModel getResepById(Long id) {
        Optional<ResepModel> resep = resepDb.findById(id);
        if (resep.isPresent()) {
            return resep.get();
        } else return null;
    }

    @Override
    public Boolean confirmResep(ResepModel resep) {
        Integer hargaResep = 0;

        for (JumlahModel jumlah : resep.getListJumlah()) {
            if (jumlah.getObat().getStok() < jumlah.getKuantitas()) {
                return false;
            }
            ObatModel obat = jumlah.getObat();
            hargaResep += obat.getHarga() * jumlah.getKuantitas();
            obat.setStok(obat.getStok() - jumlah.getKuantitas());
            obatService.updateObat(obat);
        }
        resep.setIsDone(true);

        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        resep.setApoteker((ApotekerModel) user);

        AppointmentModel appointment = resep.getAppointment();
        appointmentService.setAppointmentDone(appointment, appointment.getDokter().getTarif() + hargaResep);

        resepDb.save(resep);
        return true;
    }
}
