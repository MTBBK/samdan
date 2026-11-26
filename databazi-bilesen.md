KITAP (kitapISBN, kitapAdi, kitapTuru, kitapSayfaSayisi, kitapCikisYili, kitapBaskiSayisi)
- Primary Key: (kitapISBN)

YAZAR (yazarNumarasi, yazarAdi)
- Primary Key: (yazarNumarasi)

YAYINEVI (yayineviNumarasi, yayineviAdi)
- Primary Key: (yayineviNumarasi)

KUTUPHANE (kutuphaneNumarasi, kutuphaneAdi, kutuphaneKonumu)
- Primary Key: (kutuphaneNumarasi)

KULLANICI (kullaniciAdi, gercekAdi, sifre, yetkiSeviyesi, okurYasi, TCNO)
- Primary Key: (kullaniciAdi)
- UNIQUE: (TCNO)

DEGERLENDIRME (kitapISBN (FK: KITAP), kullaniciAdi (FK: KULLANICI), degerlendirme, puan)
- Primary Key: (kitapISBN, kullaniciAdi)

ARIYET ((kitapISBN, kopyaNumarasi , kutuphaneNumarasi) (FK: KUTUPHANE BULUNMA), kullaniciAdi (FK: KULLANICI))
- Primary Key: (kitapISBN, kopyaNumarasi , kutuphaneNumarasi)

YAZARI_OLMA (kitapISBN (FK: KITAP), yazarNumarasi (FK: YAZAR))
- Primary Key: (kitapISBN, yazarNumarasi)

YAYINEVI_OLMA (kitapISBN (FK: KITAP), yayineviNumarasi (FK: YAYINEVI))
- Primary Key: (kitapISBN, yayineviNumarasi)

KUTUPHANE_BULUNMA (kitapISBN (FK: KITAP), kopyaNumarasi, kutuphaneNumarasi (FK: KUTUPHANE))
- Primary Key: (kitapISBN, kopyaNumarasi , kutuphaneNumarasi)