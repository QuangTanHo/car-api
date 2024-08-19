package quanli.duan.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.entity.*;
import quanli.duan.repository.*;
import quanli.duan.utils.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlDataService {
    final BrandRepository brandRepository;
    final CarModelRepository carModelRepository;
    final CarDetailRepository carDetailRepository;
    final VersionRepository versionRepository;
    final CarImageRepository carImageRepository;
    final ShowRoomRepository showRoomRepository;
    public ResponseBody<Object> crawlDataToWeb() {
        String url_hang_xe = Constants.URL_CLONE.concat("/hang-xe-oto");

        BrandModel brandModel =null;
//		  set tmp
        List<BrandModel> listBrand = new ArrayList<>();
        List<CarModel> listCarModel = new ArrayList<>();
        List<CarDetailModel> listCarDetailModel = new ArrayList<>();
        try {
            Document docBrand = Jsoup.connect(url_hang_xe).get();
            Elements elements = docBrand.select("ul.list-manufacturer li a");
            for (Element element : elements) {
                String name = element.select("h3").text();
                String href = element.attr("href");
                String imageUrl = element.select("img").attr("data-src");

				brandModel = this.brandRepository.findByName(name);
                if(!Objects.isNull(brandModel)){
                    listBrand.add(brandModel);
                }else {
                    brandModel = new BrandModel();
                    brandModel.setName(name);
                    brandModel.setImageUrl(imageUrl);
                    brandModel.setHref(href.replaceAll("/","/xe-"));
                    brandModel.setType(Constants.CLONE);
                    BrandModel brandModelNew= this.brandRepository.save(brandModel);
                    listBrand.add(brandModelNew);
                }
            } //end for

            listBrand.stream().forEach(x ->{
                try {
                    Document docCar = Jsoup.connect( Constants.URL_CLONE.concat(x.getHref())).get();
                    Elements elementsCar= docCar.select("ul.cars-list li");
                    for (Element item : elementsCar) {
                        String href = item.selectFirst("a").attr("href");
                        String imageUrl = item.selectFirst("img").attr("data-src");
                        String name = item.selectFirst("h3").text();
                        Element  priceElement  = (item.selectFirst("p > b"));
                        String price =  priceElement != null ? (item.selectFirst("p > b").text()).replaceAll(",","").replaceAll("đ","") :"0" ;

                        AtomicReference<CarModel> carModelRef = new AtomicReference<>(null);
                        carModelRef.set(this.carModelRepository.findByCarName(name));
                        if (!Objects.isNull(carModelRef.get())) {
                            listCarModel.add(carModelRef.get());
                        } else {
                            CarModel newCarModel = new CarModel();
                            newCarModel.setBrandId(x.getBrandId());
                            newCarModel.setCarName(name);
                            newCarModel.setImageUrl(imageUrl);
                            newCarModel.setHref(href);
                            newCarModel.setPrice(new BigDecimal(price));
                            newCarModel.setType(Constants.CLONE);
                            carModelRef.set(this.carModelRepository.save(newCarModel));
                            listCarModel.add(newCarModel);
                        }
                    }
                    //showRoom
                    Document docShowRoom = Jsoup.connect( Constants.URL_CLONE.concat(x.getHref()).replaceAll("xe","dai-ly")).get();
                    Elements elementsShowRoomMB= docShowRoom.select("ul#dealer-list-MB li");
                    for (Element item : elementsShowRoomMB) {
                        String href = item.selectFirst("a").attr("href");
                        String imageUrl = item.selectFirst("img").attr("data-src");
                        String name = item.selectFirst("h3").text();
                        String phone = item.selectFirst("span.phone").text();
                        String address = item.selectFirst("span.address").text();
                        String area = "MB";

                        AtomicReference<ShowRoomModel> showRoomModelRef = new AtomicReference<>(null);
                        showRoomModelRef.set(this.showRoomRepository.findByName(name));
                        if (!Objects.isNull(showRoomModelRef.get())) {
                            showRoomModelRef.get().setBrandId(x.getBrandId());
                            showRoomModelRef.get().setName(name);
                            showRoomModelRef.get().setUrlImage(imageUrl);
                            showRoomModelRef.get().setHref(href);
                            showRoomModelRef.get().setHotLine(phone);
                            showRoomModelRef.get().setAddress(address);
                            showRoomModelRef.get().setArea(area);
                            showRoomModelRef.get().setType(Constants.CLONE);
                            this.showRoomRepository.save(showRoomModelRef.get());
                        } else {
                            ShowRoomModel newShowRoomModel = new ShowRoomModel();
                            newShowRoomModel.setBrandId(x.getBrandId());
                            newShowRoomModel.setName(name);
                            newShowRoomModel.setUrlImage(imageUrl);
                            newShowRoomModel.setHref(href);
                            newShowRoomModel.setHotLine(phone);
                            newShowRoomModel.setAddress(address);
                            newShowRoomModel.setArea(area);
                            newShowRoomModel.setType(Constants.CLONE);
                            showRoomModelRef.set(this.showRoomRepository.save(newShowRoomModel));
                        }
                    }

                    Elements elementsShowRoomMT= docShowRoom.select("ul#dealer-list-MB li");
                    for (Element item : elementsShowRoomMT) {
                        String href = item.selectFirst("a").attr("href");
                        String imageUrl = item.selectFirst("img").attr("data-src");
                        String name = item.selectFirst("h3").text();
                        String phone = item.selectFirst("span.phone").text();
                        String address = item.selectFirst("span.address").text();
                        String area = "MT";

                        AtomicReference<ShowRoomModel> showRoomModelRef = new AtomicReference<>(null);
                        showRoomModelRef.set(this.showRoomRepository.findByName(name));
                        if (!Objects.isNull(showRoomModelRef.get())) {
                            showRoomModelRef.get().setBrandId(x.getBrandId());
                            showRoomModelRef.get().setName(name);
                            showRoomModelRef.get().setUrlImage(imageUrl);
                            showRoomModelRef.get().setHref(href);
                            showRoomModelRef.get().setHotLine(phone);
                            showRoomModelRef.get().setAddress(address);
                            showRoomModelRef.get().setArea(area);
                            showRoomModelRef.get().setType(Constants.CLONE);
                            this.showRoomRepository.save(showRoomModelRef.get());
                        } else {
                            ShowRoomModel newShowRoomModel = new ShowRoomModel();
                            newShowRoomModel.setBrandId(x.getBrandId());
                            newShowRoomModel.setName(name);
                            newShowRoomModel.setUrlImage(imageUrl);
                            newShowRoomModel.setHref(href);
                            newShowRoomModel.setHotLine(phone);
                            newShowRoomModel.setAddress(address);
                            newShowRoomModel.setArea(area);
                            newShowRoomModel.setType(Constants.CLONE);
                            showRoomModelRef.set(this.showRoomRepository.save(newShowRoomModel));
                        }
                    }
                    Elements elementsShowRoomMN= docShowRoom.select("ul#dealer-list-MN li");
                    for (Element item : elementsShowRoomMN) {
                        String href = item.selectFirst("a").attr("href");
                        String imageUrl = item.selectFirst("img").attr("data-src");
                        String name = item.selectFirst("h3").text();
                        String phone = item.selectFirst("span.phone").text();
                        String address = item.selectFirst("span.address").text();
                        String area = "MN";

                        AtomicReference<ShowRoomModel> showRoomModelRef = new AtomicReference<>(null);
                        showRoomModelRef.set(this.showRoomRepository.findByName(name));
                        if (!Objects.isNull(showRoomModelRef.get())) {
                            showRoomModelRef.get().setBrandId(x.getBrandId());
                            showRoomModelRef.get().setName(name);
                            showRoomModelRef.get().setUrlImage(imageUrl);
                            showRoomModelRef.get().setHref(href);
                            showRoomModelRef.get().setHotLine(phone);
                            showRoomModelRef.get().setAddress(address);
                            showRoomModelRef.get().setArea(area);
                            showRoomModelRef.get().setType(Constants.CLONE);
                            this.showRoomRepository.save(showRoomModelRef.get());
                        } else {
                            ShowRoomModel newShowRoomModel = new ShowRoomModel();
                            newShowRoomModel.setBrandId(x.getBrandId());
                            newShowRoomModel.setName(name);
                            newShowRoomModel.setUrlImage(imageUrl);
                            newShowRoomModel.setHref(href);
                            newShowRoomModel.setHotLine(phone);
                            newShowRoomModel.setAddress(address);
                            newShowRoomModel.setArea(area);
                            newShowRoomModel.setType(Constants.CLONE);
                            showRoomModelRef.set(this.showRoomRepository.save(newShowRoomModel));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }); //end for

            listCarModel.stream().forEach(x ->{
                try {
                    Document docCar = Jsoup.connect( Constants.URL_CLONE.concat(x.getHref())).get();

                    Elements elementsCarImage= docCar.select("#carImageThumb img");
                    for (Element item : elementsCarImage) {
                        String imageUrl = item.attr("data-src");

                        AtomicReference<CarImage> carImageModelRef = new AtomicReference<>(null);
                        carImageModelRef.set(this.carImageRepository.findByUrlImage(imageUrl));
                        if (!Objects.isNull(carImageModelRef.get())) {
                            carImageModelRef.get().setCarModelId(x.getCarModelId());
                            carImageModelRef.get().setUrlImage(imageUrl);
                            carImageModelRef.get().setType(Constants.CLONE);
                            this.carImageRepository.save(carImageModelRef.get());
                        } else {
                            CarImage newCarImageModel = new CarImage();
                            newCarImageModel.setCarModelId(x.getCarModelId());
                            newCarImageModel.setUrlImage(imageUrl);
                            newCarImageModel.setType(Constants.CLONE);
                            this.carImageRepository.save(newCarImageModel);
                        }
                    }

                    Elements elementsCarDetail= docCar.select(".carLeft .sTable");
                    Element rowThuonghieu = elementsCarDetail.get(0).selectFirst("tr:contains(" + "Thương hiệu" + ")");
                    String thuongHieu  ="";
                    if(rowThuonghieu != null){
                        thuongHieu= rowThuonghieu.select("td").get(1).text().trim();
                    }

                    Element rowLoaiXe= elementsCarDetail.get(0).selectFirst("tr:contains(" + "Loại xe" + ")");
                    String loaiXe ="";
                    if(rowLoaiXe != null){
                        loaiXe= rowLoaiXe.select("td").get(1).text().trim();
                    }

                    Element rowPhanKhuc = elementsCarDetail.get(0).selectFirst("tr:contains(" + "Phân khúc" + ")");
                    String phanKhuc ="";
                    if(rowPhanKhuc != null){
                        phanKhuc  = rowPhanKhuc.select("td").get(1).text().trim();
                    }
                    Element rowXuatxu = elementsCarDetail.get(0).selectFirst("tr:contains(" + "Xuất xứ" + ")");
                    String xuatxu ="";
                    if(rowXuatxu !=null) {
                        xuatxu = rowXuatxu.select("td").get(1).text().trim();
                    }
                    Element rowMucGia = elementsCarDetail.get(0).selectFirst("tr:contains(" + "Mức giá" + ")");
                    String mucGia ="";
                    if(rowMucGia !=null) {
                        mucGia = rowMucGia.select("td").get(1).text().trim();
                    }
                    Elements elementsthongsokythuat= docCar.select("#carRight2 .sTable");
                    String socho ="";
                    String dairongcao="";
                    String chieudaicoso= "";
                    String khoangcachgamxe="";
                    String vanhvalop="";
                    String dongco="";
                    String congsuat="";
                    String momen="";
                    String hopso="";
                    String hethongdandong="";
                    String hethongtreo="";
                    String phanhtruoc="";
                    String congnghephanh="";
                    boolean isUpdatethongsokythuat =false;
                    if(elementsthongsokythuat.size() > 0){
                        isUpdatethongsokythuat = true;
                        Element rowSoCho= elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Số chỗ" + ")");
                        if(rowSoCho != null){
                            socho = rowSoCho.select("td").get(1).text().trim();
                        }
                        Element rowDairongcao= elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Dài x Rộng x Cao (mm)" + ")");
                        if(rowDairongcao != null){
                            dairongcao = rowDairongcao.select("td").get(1).text().trim();
                        }
                        Element rowChieudaicoso= elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Chiều dài cơ sở (mm)" + ")");
                        if(rowChieudaicoso != null){
                            chieudaicoso = rowChieudaicoso.select("td").get(1).text().trim();
                        }
                        Element rowKhoangcachgamxe= elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Khoảng sáng gầm xe (mm)" + ")");
                        if(rowKhoangcachgamxe != null){
                            khoangcachgamxe = rowKhoangcachgamxe.select("td").get(1).text().trim();
                        }
                        Element rowVanhvalop= elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Vành và Lốp xe" + ")");
                        if(rowVanhvalop != null){
                            vanhvalop = rowVanhvalop.select("td").get(1).text().trim();
                        }
                        Elements rowDongCo = elementsthongsokythuat.get(0).select("tr:contains(Động cơ)");
                        if(rowDongCo != null){
                            dongco = rowDongCo.select("td").get(1).text().trim();
                        }
                        Element rowCongsuat= elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Công suất (Hp/rpm)" + ")");
                        if(rowCongsuat != null){
                            congsuat = rowCongsuat.select("td").get(1).text().trim();
                        }
                        Element rowMomen= elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Mô men xoắn (Nm/rpm)" + ")");
                        if(rowMomen != null){
                            momen = rowMomen.select("td").get(1).text().trim();
                        }
                        Elements rowHopso = elementsthongsokythuat.get(0).select("tr:contains(Hộp số)");
                        if(rowHopso != null){
                            hopso = rowHopso.select("td").get(1).text().trim();
                        }
                        Element rowHethongbandan= elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Hệ thống dẫn động" + ")");
                        if(rowHethongbandan != null){
                            hethongdandong = rowHethongbandan.select("td").get(1).text().trim();
                        }
                        Element rowHethongtreo =elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Hệ thống treo trước/sau" + ")");
                        if(rowHethongtreo != null){
                            hethongtreo = rowHethongtreo.select("td").get(1).text().trim();
                        }

                        Element rowPhanhtruoc= elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Phanh trước/sau" + ")");
                        if(rowPhanhtruoc != null){
                            phanhtruoc= rowPhanhtruoc.select("td").get(1).text().trim();
                        }
                        Element rowCongnghephanh =elementsthongsokythuat.get(0).selectFirst("tr:contains(" + "Công nghệ phanh" + ")");
                        if(rowCongnghephanh != null){
                            congnghephanh = rowCongnghephanh.select("td").get(1).text().trim();
                        }
                    }
                    AtomicReference<CarDetailModel> carDetailModelRef = new AtomicReference<>(null);
                    carDetailModelRef.set(this.carDetailRepository.findByCarDetailName(x.getCarName()));
                    if (!Objects.isNull(carDetailModelRef.get())) {
                        if(isUpdatethongsokythuat){
                            //   chỗ ngồi
                            carDetailModelRef.get().setSeat(socho);

                            carDetailModelRef.get().setHWW(dairongcao);
                            //    Khoảng sáng gầm xe
                            carDetailModelRef.get().setGroundClearance(khoangcachgamxe);
                            //    Vành và Lốp xe
                            carDetailModelRef.get().setWheelsAndTires(vanhvalop);
                            //    Động cơ
                            carDetailModelRef.get().setEngine(dongco);
                            //    Công suất
                            carDetailModelRef.get().setPower(congsuat);
                            //    Mô men xoắn
                            carDetailModelRef.get().setTorque(momen);
                            //    Hộp số
                            carDetailModelRef.get().setTransmission(hopso);
                            //    Hệ thống dẫn động
                            carDetailModelRef.get().setDriveSystem(hethongdandong);
                            //    Hệ thống treo trước/sau
                            String suspensionSystem;
                            carDetailModelRef.get().setSuspensionSystem(hethongtreo);
                            //    Phanh trước/sau
                            carDetailModelRef.get().setRearBrake(phanhtruoc);
                            //    Phanh trước/sau
                            carDetailModelRef.get().setBrakeTechnology(congnghephanh);
                            listCarDetailModel.add(this.carDetailRepository.save(carDetailModelRef.get()));
                        }
                        listCarDetailModel.add(carDetailModelRef.get());
                    } else {
                        CarDetailModel carDetailModel = new CarDetailModel();
                        carDetailModel.setCarModelId(x.getCarModelId());
                        carDetailModel.setCarDetailName(x.getCarName());
                        carDetailModel.setBrandname(thuongHieu);
                        carDetailModel.setTypeOfvehicle(loaiXe);
                        carDetailModel.setSegment(phanKhuc);
                        carDetailModel.setOrigin(xuatxu);
                        carDetailModel.setPriceRange(mucGia);
                        //   chỗ ngồi
                        carDetailModel.setSeat(socho);
                        carDetailModel.setHWW(chieudaicoso);
                        //    Khoảng sáng gầm xe
                        carDetailModel.setGroundClearance(khoangcachgamxe);
                        //    Vành và Lốp xe
                        carDetailModel.setWheelsAndTires(vanhvalop);
                        //    Động cơ
                        carDetailModel.setEngine(dongco);
                        //    Công suất
                        carDetailModel.setPower(congsuat);
                        //    Mô men xoắn
                        carDetailModel.setTorque(momen);
                        //    Hộp số
                        carDetailModel.setTransmission(hopso);
                        //    Hệ thống dẫn động
                        carDetailModel.setDriveSystem(hethongdandong);
                        //    Hệ thống treo trước/sau
                        String suspensionSystem;
                        carDetailModel.setSuspensionSystem(hethongtreo);
                        //    Phanh trước/sau
                        carDetailModel.setRearBrake(phanhtruoc);
                        //    Phanh trước/sau
                        carDetailModel.setBrakeTechnology(congnghephanh);
                        carDetailModel.setType(Constants.CLONE);
                        carDetailModelRef.set(this.carDetailRepository.save(carDetailModel));
                        listCarDetailModel.add(carDetailModel);
                    }

                    Elements rows = docCar.select(".car-option-price tbody tr:not(:first-child)");

                    // Check if there are any rows
                    if (!rows.isEmpty()) {
                        for (Element row : rows) {
                            Elements cells = row.select("td");
                            String phienBan = cells.get(0).text();
                            String giaCongBo = cells.get(1).text();
                            String lanBanhHCM = cells.get(2).text();
                            String lanBanhHN = cells.get(3).text();
                            String lanBanhTinh = cells.get(4).text();
                            AtomicReference<VersionModel> versionModelRef = new AtomicReference<>(null);
                            versionModelRef.set(this.versionRepository.findByVersionName(x.getCarName()));
                            if (!Objects.isNull(versionModelRef.get())) {
                                versionModelRef.get().setVersionName(phienBan);
                                versionModelRef.get().setInstallmentPayment(new BigDecimal(giaCongBo.replaceAll(",","").replaceAll("₫","")));
                                versionModelRef.get().setOnRoadPriceHN(new BigDecimal(lanBanhHN.replaceAll(",","").replaceAll(" triệu","000000")));
                                versionModelRef.get().setOnRoadPriceHCM(new BigDecimal(lanBanhHCM.replaceAll(",","").replaceAll(" triệu","000000")));
                                versionModelRef.get().setOnRoadPriceProvince(new BigDecimal(lanBanhTinh.replaceAll(",","").replaceAll(" triệu","000000")));
                                this.versionRepository.save(versionModelRef.get());
                            }else{
                                VersionModel newVersionModel =new  VersionModel();
                                newVersionModel.setCarModelId(x.getCarModelId());
                                newVersionModel.setVersionName(phienBan);
                                newVersionModel.setInstallmentPayment(new BigDecimal(giaCongBo.replaceAll(",","").replaceAll("₫","")));
                                newVersionModel.setOnRoadPriceHN(new BigDecimal(lanBanhHN.replaceAll(",","").replaceAll(" triệu","000000")));
                                newVersionModel.setOnRoadPriceHCM(new BigDecimal(lanBanhHCM.replaceAll(",","").replaceAll(" triệu","000000")));
                                newVersionModel.setOnRoadPriceProvince(new BigDecimal(lanBanhTinh.replaceAll(",","").replaceAll(" triệu","000000")));
                                this.versionRepository.save(newVersionModel);
                            }

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });//end for

        } catch (IOException e) {
            e.printStackTrace();
        }
         return null;
    }
}
