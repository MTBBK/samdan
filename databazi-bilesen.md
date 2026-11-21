KITAP (kitapISBN (Key), kitapAdi, kitapTuru, kitapSayfaSayisi, kitapCikisYili, kitapBaskiSayisi)

YAZAR ( yazarNumarasi (Key), yazarAdi)

YAYINEVI (yayineviNumarasi (Key), yayineviAdi)

KUTUPHANE (kutuphaneNumarasi (Key), kutuphaneAdi, kutuphaneKonumu)

KULLANICI (kullaniciNumarasi (Key), kullaniciAdi, sifre, yetkiSeviyesi, okurYasi)

ARIYET (kitapISBN (FK: KITAP), kullaniciAdi (FK: KULLANICI))

YAZARI OLMA (kitapISBN (FK: KITAP), yazarNumarasi (FK: YAZAR))

YAYINEVI OLMA (kitapISBN (FK: KITAP), yayineviNumarasi (FK: YAYINEVI))

KUTUPHANE BULUNMA (kitapISBN (FK: KITAP), kutuphaneNumarasi (FK: KUTUPHANE))