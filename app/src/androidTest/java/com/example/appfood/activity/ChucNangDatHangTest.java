package com.example.appfood.activity;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.appfood.R;
import com.example.lib.common.Show;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DecimalFormat;
import java.util.ArrayList;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChucNangDatHangTest {
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    ArrayList<String> nameProduct = new ArrayList<String>();
    ArrayList<Integer> priceProduct = new ArrayList<Integer>();

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void themGioHangTest() {
        //T???o data test
        int viTriSP = 0;
        int soLuong = 5; //ch???n theo c??c m???c: 1, 5, 10, 15, 20, 50, 100
        addToCart(viTriSP, soLuong);
        openCart(cartPosition.DETAIL);
        //Ki???m tra s???n c?? s???n ph???m trong gi??? h??ng hay kh??ng
        ViewInteraction cardItem = onView(
                allOf(withParent(allOf(withId(R.id.recycleView_Giohang))), isDisplayed()));
        cardItem.check(matches(isDisplayed()));
        //Ki???m tra t??n s???n ph???m
        ViewInteraction tenmon_giohang = onView(
                allOf(withId(R.id.tenmon_giohang), isDisplayed()));
        tenmon_giohang.check(matches(withText(nameProduct.get(0))));
        //Ki???m tra s??? l?????ng s???n ph???m
        ViewInteraction soluong_mon = onView(
                allOf(withId(R.id.soluong_mon), isDisplayed()));
        soluong_mon.check(matches(withText(soLuong + " ")));
    }

    @Test
    public void thongBaoSoLuongTest() {
        //T???o data test
        int[] viTriSP = {0, 1};
        int[] soLuong = {5, 1};

        addToCart(viTriSP[0], soLuong[0]);

        //Ki???m tra th??ng b??o c?? ????ng l?? 5 hay kh??ng
        ViewInteraction thongbao_soluong = onView(allOf(withId(R.id.thongbao_soluong),
                isDisplayed()));
        thongbao_soluong.check(matches(withText(String.valueOf(soLuong[0]))));

        back();
        addToCart(viTriSP[1], soLuong[1]);
        //Ki???m tra th??ng b??o c?? ????ng l?? 6 hay kh??ng
        thongbao_soluong.check(matches(withText(String.valueOf(soLuong[0] + soLuong[1]))));
    }

    @Test
    public void tongTienTest() {
        //T???o data test
        int[] viTriSP = {0, 1};
        int[] soLuong = {5, 1};
        int viTriSPCong = 1;
        int soLanCong = 2;
        int viTriSPTru = 0;
        int soLanTru = 1;
        addToCart(viTriSP[0], soLuong[0]);
        back();
        addToCart(viTriSP[1], soLuong[1]);
        openCart(cartPosition.DETAIL);
        //Ki???m tra t???ng ti???n c??c m???t h??ng trong gi??? h??ng
        ViewInteraction textview_tongtien = onView(
                allOf(withId(R.id.textview_tongtien), isDisplayed()));
        int money = 0;
        for (int i = 0; i < viTriSP.length; i++) {
            money = money + priceProduct.get(i) * soLuong[i];
        }
        textview_tongtien.check(matches(withText(decimalFormat.format(money) + " ??")));

        //t??ng s??? l?????ng 1 s???n ph???m v?? ki???m tra t???ng ti???n
        ViewInteraction cong_giohang = onView(
                allOf(withId(R.id.cong_giohang), withContentDescription(String.valueOf(viTriSPCong)),
                        isDisplayed()));
        for (int i = 0; i < soLanCong; i++) {
            cong_giohang.perform(click());
        }
        money = money + priceProduct.get(viTriSPCong) * soLanCong;
        textview_tongtien.check(matches(withText(decimalFormat.format(money) + " ??")));

        //Tr??? s??? l?????ng s???n ph???m v?? ki???m tra t???ng ti???n
        ViewInteraction tru_giohang = onView(
                allOf(withId(R.id.tru_giohang), withContentDescription(String.valueOf(viTriSPTru)),
                        isDisplayed()));
        for (int i = 0; i < soLanTru; i++) {
            tru_giohang.perform(click());
        }
        money = money - priceProduct.get(viTriSPTru) * soLanTru;
        textview_tongtien.check(matches(withText(decimalFormat.format(money) + " ??")));
    }

    @Test
    public void xoaGioHangTest() {
        //T???o data test
        int[] viTriSP = {0, 1};
        int[] soLuong = {5, 1}; //ch???n theo c??c m???c: 1, 5, 10, 15, 20, 50, 100

        //Th??m s???n ph???m 1
        addToCart(viTriSP[0], soLuong[0]);
        back();
        //Th??m s???n ph???m 2
        addToCart(viTriSP[1], soLuong[1]);
        openCart(cartPosition.DETAIL);

        //Hu??? l???nh xo?? s???n ph???m s??? 1
        deleteFromCart(viTriSP[0], false);
        //ki???m tra s???n ph???m 1 c??n trong gi??? h??ng kh??ng
        ViewInteraction sanPham1 = onView(
                allOf(withParent(allOf(withId(R.id.recycleView_Giohang))),
                        withContentDescription(String.valueOf(viTriSP[0])),
                        isDisplayed()));
        sanPham1.check(matches(isDisplayed()));
        //Xo?? s???n ph???m 2
        deleteFromCart(viTriSP[1], true);
        //ki???m tra s???n ph???m 2 ???? m???t hay ch??a
        ViewInteraction sanPham2 = onView(
                allOf(withParent(allOf(withId(R.id.recycleView_Giohang))),
                        withContentDescription(String.valueOf(viTriSP[1])),
                        isDisplayed()));
        sanPham2.check(doesNotExist());
        //Xo?? s???n ph???m 1
        deleteFromCart(viTriSP[0], true);
        //ki???m tra s???n ph???m 1 ???? m???t hay ch??a
        sanPham1.check(doesNotExist());
    }

    @Test
    public void GioHangTrongTest() {
        openCart(cartPosition.HOME);
        datHang();

        ViewInteraction message_order = onView(
                allOf(withId(R.id.message_order), withText("Vui l??ng ch???n m??n tr?????c khi ?????t h??ng!")));
        message_order.check(matches(isDisplayed()));

        ViewInteraction nameScreen = onView(
                allOf(withParent(allOf(withId(R.id.toolbarGiohang))),
                        withText("Gi??? h??ng")));
        nameScreen.check(matches(isDisplayed()));
    }

    @Test
    public void khongDienThongTinTest() {

        //data test
        int[] viTriSP = {0, 1, 3};
        int[] soLuong = {5, 1, 10};

        for (int i = 0; i < viTriSP.length; i++) {
            addToCart(viTriSP[i], soLuong[i]);
            back();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        openCart(cartPosition.HOME);
        datHang();
        xacNhanDatHang();

        ViewInteraction message_name = onView(
                allOf(withId(R.id.message_name), withText("Vui l??ng nh???p t??n kh??ch h??ng!")));
        message_name.check(matches(isDisplayed()));

        ViewInteraction message_address = onView(
                allOf(withId(R.id.message_address), withText("Vui l??ng nh???p ?????a ch???!")));
        message_address.check(matches(isDisplayed()));

        ViewInteraction message_phone = onView(
                allOf(withId(R.id.message_phone), withText("Vui l??ng nh???p s??? ??i???n tho???i!")));
        message_phone.check(matches(isDisplayed()));
    }

    @Test
    public void invalidSDTTest() {
        int[] viTriSP = {0};
        int[] soLuong = {5};
        String name = "L??m Anh Tu???n";
        String address = "279 Nguy???n Tri Ph????ng, P.15, Q.10";
        String phone1 = "03460664767"; // >11 number
        String phone2 = "abc";
        String phone3 = "034606.45+";

        addToCart(viTriSP[0], soLuong[0]);
        openCart(cartPosition.DETAIL);

        datHang();

        ViewInteraction hoVaTen = onView(allOf(withId(R.id.user_name)));
        hoVaTen.perform(replaceText(name), closeSoftKeyboard());

        ViewInteraction diaChi = onView(allOf(withId(R.id.user_address)));
        diaChi.perform(replaceText(address), closeSoftKeyboard());

        ViewInteraction soDienThoai = onView(allOf(withId(R.id.user_phone)));
        soDienThoai.perform(replaceText(phone1), closeSoftKeyboard());
        xacNhanDatHang();
        ViewInteraction message1 = onView(
                allOf(withId(R.id.message_phone),
                        withText("S??? ??i???n tho???i kh??ng ???????c v?????t qu?? 10 k?? t???!")));
        message1.check(matches(isDisplayed()));

        soDienThoai.perform(replaceText(phone2), closeSoftKeyboard());
        xacNhanDatHang();
        ViewInteraction message2 = onView(
                allOf(withId(R.id.message_phone),
                        withText("S??? ??i???n tho???i kh??ng ???????c ch???a ch??? c??i ho???c k?? t??? ?????c bi???t!")));
        message2.check(matches(isDisplayed()));

        soDienThoai.perform(replaceText(phone3), closeSoftKeyboard());
        xacNhanDatHang();
        message2.check(matches(isDisplayed()));
    }

    @Test
    public void datHangDayDuThongTinTest() {
        int[] viTriSP = {0};
        int[] soLuong = {5};
        String name = "L??m Anh Tu???n";
        String address = "279 Nguy???n Tri Ph????ng, P.15, Q.10";
        String phone = "0346066476";
        String note = "Nhi???u ???t";

        addToCart(viTriSP[0], soLuong[0]);
        openCart(cartPosition.DETAIL);
        datHang();

        ViewInteraction user_name = onView(allOf(withId(R.id.user_name)));
        user_name.perform(replaceText(name), closeSoftKeyboard());

        ViewInteraction user_address = onView(allOf(withId(R.id.user_address)));
        user_address.perform(replaceText(address), closeSoftKeyboard());

        ViewInteraction user_phone = onView(allOf(withId(R.id.user_phone)));
        user_phone.perform(replaceText(phone), closeSoftKeyboard());

        ViewInteraction user_note = onView(allOf(withId(R.id.user_note)));
        user_note.perform(scrollTo(), replaceText(note), closeSoftKeyboard());

        xacNhanDatHang();

        onView(allOf(withText("Th??nh c??ng"))).check(matches(isDisplayed()));
        int money = 0;
        for (int i = 0; i < viTriSP.length; i++) {
            money = money + priceProduct.get(i) * soLuong[0];
        }
        ViewInteraction txt_tongtien = onView(allOf(withId(R.id.txt_tongtien), isDisplayed()));
        txt_tongtien.check(matches(withText(decimalFormat.format(money) + " ??")));

        ViewInteraction txt_tenkhachhang = onView(allOf(withId(R.id.txt_tenkhachhang), isDisplayed()));
        txt_tenkhachhang.check(matches(withText(name)));

        ViewInteraction txt_address = onView(allOf(withId(R.id.txt_address), isDisplayed()));
        txt_address.check(matches(withText(address)));

        ViewInteraction txt_sodienthoai = onView(allOf(withId(R.id.txt_sodienthoai), isDisplayed()));
        txt_sodienthoai.check(matches(withText(phone)));

        ViewInteraction textView6 = onView( allOf(withId(R.id.txt_ghichu), isDisplayed()));
        textView6.check(matches(withText(note)));
    }


    @After
    public void reStart() {
        nameProduct.clear();
        priceProduct.clear();
        Show.listGiohang.clear();
        Espresso.pressBack();
    }


    //------------------C??c h??m c???n thi???t-----------------------//


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static int layViTriTheoSoLuong(int soluong) {
        switch (soluong) {
            case 1:
                return 0;
            case 5:
                return 1;
            case 10:
                return 2;
            case 15:
                return 3;
            case 20:
                return 4;
            case 50:
                return 5;
            case 100:
                return 6;
            default:
                return 0;
        }
    }

    public static String getText(final Matcher<View> matcher) {
        try {
            final String[] stringHolder = {null};
            onView(matcher).perform(new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return isAssignableFrom(TextView.class);
                }

                @Override
                public String getDescription() {
                    return "get text";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    TextView tv = (TextView) view;
                    stringHolder[0] = tv.getText().toString();
                }
            });
            if (stringHolder[0] == null || stringHolder[0] == "") {
                fail("no text found");
            }
            return stringHolder[0];
        } catch (Exception e) {
            fail("null found");
            return null;
        }
    }

    public static int getPrice() {
        String text = getText(allOf(withId(R.id.gia_chitiet),
                withParent(allOf(withId(R.id.linear_thongtin),
                        withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                isDisplayed()));
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    public void addToCart(int viTriSP, int soLuong) {
        //Ch???n s???n ph???m
        ViewInteraction monAn = onView(
                allOf(withId(R.id.recycleView_MonNgauNhien)));
        monAn.perform(actionOnItemAtPosition(viTriSP, click()));
        //L???y t??n s???n ph???m
        String name = getText(allOf(withId(R.id.tenmon_chitiet),isDisplayed()));
        nameProduct.add(name);
        //L???y gi?? s???n ph???m
        priceProduct.add(getPrice());
        //M??? spinner s??? l?????ng
        ViewInteraction soLuongButton = onView(
                allOf(withId(R.id.spinner_soluong), isDisplayed()));
        soLuongButton.perform(click());
        //Ch???n s??? l?????ng
        DataInteraction selectSL = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(layViTriTheoSoLuong(soLuong));
        selectSL.perform(click());
        //B???m th??m v??o gi??? h??ng
        ViewInteraction addButton = onView(
                allOf(withId(R.id.btn_mua), isDisplayed()));
        addButton.perform(click());
    }

    enum cartPosition {
        HOME,
        DETAIL
    }

    public void openCart(cartPosition position) {
        ViewInteraction gioHang = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(position == cartPosition.DETAIL ?
                                                R.id.toolbar_Chitietmon :
                                                R.id.toolbar_Home),
                                        2),
                                0),
                        isDisplayed()));
        gioHang.perform(click());
    }

    public void back() {
        ViewInteraction back = onView(
                allOf(withContentDescription("Navigate up"), isDisplayed()));
        back.perform(click());
    }

    public void deleteFromCart(int position, boolean isConfirm) {
        ViewInteraction xoaSP = onView(
                allOf(withId(R.id.xoa_giohang), withContentDescription(String.valueOf(position)),
                        isDisplayed()));
        xoaSP.perform(click());

        ViewInteraction confirm = onView(
                allOf(withId(isConfirm ? R.id.btn_yes : R.id.btn_no), isDisplayed()));
        confirm.perform(click());
    }

    public void datHang() {
        ViewInteraction datHang = onView(
                allOf(withId(R.id.btn_thanhtoan), isDisplayed()));
        datHang.perform(click());
    }

    public void xacNhanDatHang() {
        ViewInteraction xacNhanDatHang = onView(
                allOf(withId(R.id.btn_xacnhanthanhtoan)));
        xacNhanDatHang.perform(click());
    }


}
