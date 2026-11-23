KITAP (kitapISBN, kitapAdi, kitapTuru, kitapSayfaSayisi, kitapCikisYili, kitapBaskiSayisi)
- Primary Key: (kitapISBN)

YAZAR (yazarNumarasi, yazarAdi)
- Primary Key: (yazarNumarasi)

YAYINEVI (yayineviNumarasi, yayineviAdi)
- Primary Key: (yayineviNumarasi)

KUTUPHANE (kutuphaneNumarasi, kutuphaneAdi, kutuphaneKonumu)
- Primary Key: (kutuphaneNumarasi)

KULLANICI (kullaniciAdi, gercekAdi, sifre, yetkiSeviyesi, okurYasi, tcno)
- Primary Key: (kullaniciAdi)

DEGERLENDIRME (kitapISBN (FK: KITAP), kullaniciAdi (FK: KULLANICI), degerlendirme, puan)
- Primary Key: (kitapISBN, kullaniciAdi)

ARIYET ((kitapISBN, kopyaNumarasi , kutuphaneNumarasi) (FK: KUTUPHANE BULUNMA), kullaniciAdi (FK: KULLANICI))
- Primary Key: (kitapISBN, kopyaNumarasi , kutuphaneNumarasi)

YAZARI OLMA (kitapISBN (FK: KITAP), yazarNumarasi (FK: YAZAR))
- Primary Key: (kitapISBN, yazarNumarasi)

YAYINEVI OLMA (kitapISBN (FK: KITAP), yayineviNumarasi (FK: YAYINEVI))
- Primary Key: (kitapISBN, yayineviNumarasi)

KUTUPHANE BULUNMA (kitapISBN (FK: KITAP), kopyaNumarasi, kutuphaneNumarasi (FK: KUTUPHANE))
- Primary Key: (kitapISBN, kopyaNumarasi , kutuphaneNumarasi)