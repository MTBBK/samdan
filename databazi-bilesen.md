KITAP (kitapISBN (Key), kitapAdi, kitapTuru, kitapSayfaSayisi, kitapCikisYili, kitapBaskiSayisi)

KOPYA (kitapISBN (FK: KITAP), KopyaNumarasi)

YAZAR (yazarNumarasi (Key), yazarAdi)

YAYINEVI (yayineviNumarasi (Key), yayineviAdi)

KUTUPHANE (kutuphaneNumarasi (Key), kutuphaneAdi, kutuphaneKonumu)

KULLANICI (kullaniciAdi (Key), gercekAdi, sifre, yetkiSeviyesi, okurYasi)

DEGERLENDIRME (kitapISBN (FK: KITAP), kullaniciAdi (FK: KULLANICI), degerlendirme, puan)

ARIYET (kitapISBN (FK: KITAP), KopyaNumarasi (FK: KOPYA), kullaniciAdi (FK: KULLANICI))

YAZARI OLMA (kitapISBN (FK: KITAP), yazarNumarasi (FK: YAZAR))

YAYINEVI OLMA (kitapISBN (FK: KITAP), yayineviNumarasi (FK: YAYINEVI))

KUTUPHANE BULUNMA (kitapISBN (FK: KITAP), kutuphaneNumarasi (FK: KUTUPHANE))
