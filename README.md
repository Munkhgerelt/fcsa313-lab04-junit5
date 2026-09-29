# Лаборатори №4 — Нэгжийн тест, JUnit 5

**Оюутны нэр:** М.Мөнхгэрэлт

**Оюутны код:** B232270012

**Хичээл:** F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест

## Ажиллуулсан орчин

`java -version` — [бодит гаралт](results/java-version.txt):

```text
openjdk version "21.0.1" 2023-10-17 LTS
OpenJDK Runtime Environment Temurin-21.0.1+12 (build 21.0.1+12-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.1+12 (build 21.0.1+12-LTS, mixed mode, sharing)
```

`mvn -version` — [бодит гаралт](results/mvn-version.txt):

```text
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: C:\Program Files\apache-maven-3.9.9
Java version: 21.0.1, vendor: Eclipse Adoptium, runtime: C:\Program Files\Eclipse Adoptium\jdk-21.0.1.12-hotspot
Default locale: en_US, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

Кодыг OpenJDK 21 дээр ажиллуулж, `maven.compiler.release=17` тохиргоогоор Java 17-д зориулан хөрвүүлэв.
JUnit Jupiter **5.10.2**, Maven Surefire **3.2.5** ашиглав.
Эхний шалгалтыг суулгагдсан Oracle JDK 22 дээр хийсэн боловч эцсийн хоёр логийг OpenJDK 21 дээр дахин үүсгэсэн.

## Хэрэгжүүлэлт

[GradeCalculator.java](src/main/java/mn/edu/must/sqat/GradeCalculator.java) хоёр үндсэн методтой.

| Метод | Дүрэм |
| --- | --- |
| `letterGrade(score)` | `[90,100] → A`, `[80,90) → B`, `[70,80) → C`, `[60,70) → D`, `[0,60) → F` |
| `totalScore(att, lab, quiz1, quiz2, exam)` | Ирц `[0,10]`, лаборатори ба бие даалт `[0,40]`, сорил тус бүр `[0,10]`, шалгалт `[0,30]`; эдгээр оноог шууд нэмнэ. |

Зөвшөөрөгдсөн хязгаараас гарсан утгад хоёр метод хоёулаа `IllegalArgumentException` шиднэ.
Нэмэлтээр `NaN`, эерэг болон сөрөг хязгааргүй утгыг хориглоно.
`totalScore` нь бүрэлдэхүүн бүрийг тусад нь шалгадаг: нийлбэр 100-аас бага байсан ч аль нэг бүрэлдэхүүн хязгаараа давбал алдаа гарна.

## Тестийн метод ба гүйцэтгэлийн тоо

[GradeCalculatorTest.java](src/test/java/mn/edu/must/sqat/GradeCalculatorTest.java): **15 тестийн метод** = **8 `@Test` + 7 `@ParameterizedTest`**.
Surefire-ийн эцсийн **Tests run: 51**; параметрийн мөр бүр тусдаа гүйцэтгэлд тооцогдоно.
Метод бүр Arrange–Act–Assert бүтэцтэй, монгол `@DisplayName`-тай.
Exception шалгах үйлдлийг `Executable` дотор бэлдэж, `assertThrows` ажиллуулах үед гүйцэтгэнэ.

| Тестийн метод | Шалгах зүйл | Гүйцэтгэл |
| --- | --- | ---: |
| `ninetyIsExactlyA` | `90 → A` | 1 |
| `justBelowNinetyIsB` | `89.99 → B` | 1 |
| `sixtyIsExactlyD` | `60 → D` | 1 |
| `justBelowSixtyIsF` | `59.99 → F` | 1 |
| `zeroIsF` | `0 → F` | 1 |
| `hundredIsA` | `100 → A` | 1 |
| `negativeLetterScoreThrows` | `-1` оноог хориглоно | 1 |
| `aboveHundredThrows` | `101` оноог хориглоно | 1 |
| `typicalLetterGrades` | `95 → A`, `85 → B`, `75 → C`, `65 → D`, `30 → F` | 5 |
| `additionalLetterBoundaries` | `80`, `79.99`, `70`, `69.99` | 4 |
| `validComponentTotals` | Бүх дээд хязгаар, тэг, ердийн болон бутархай нийлбэр | 6 |
| `negativeComponentsThrow` | Таван бүрэлдэхүүн тус бүрийн сөрөг утга | 5 |
| `aboveMaximumComponentsThrow` | Таван бүрэлдэхүүн тус бүрийн хэтэрсэн утга | 5 |
| `nonFiniteLetterScoresThrow` | `NaN`, `+Infinity`, `-Infinity` | 3 |
| `nonFiniteComponentsThrow` | Таван бүрэлдэхүүн × гурван буруу утга | 15 |
| **Нийт** | **15 метод** | **51** |

Бутархай нийлбэрийг `assertEquals(expected, actual, 1e-9)` ашиглан шалгасан.
Энэ зөрүүний хүлцэл нь хөвөгч таслалтай тооны дүрслэлийн өчүүхэн зөрүүг зөвшөөрнө.

## Бодит үр дүн ба мутаци

| Хувилбар | Tests run | Failures | Errors | Skipped | Үр дүн | Maven exit code |
| --- | ---: | ---: | ---: | ---: | --- | ---: |
| Засварыг буцаасны дараах эцсийн хувилбар | 51 | 0 | 0 | 0 | BUILD SUCCESS | 0 |
| `score >= 90`-ийг `score > 90` болгосон мутаци | 51 | 1 | 0 | 0 | BUILD FAILURE | 1 |

- [Эцсийн амжилттай тестийн бүтэн лог](results/mvn-test.txt)
- [Мутацийн бүтэн лог](results/mvn-test-mutant.txt)
- [Зориуд хийсэн өөрчлөлт](results/mutation.patch)
- [Exit code болон сэргээсэн төлөв](results/mutation-summary.txt)

Мутацид `GradeCalculatorTest.ninetyIsExactlyA` унасан:

```text
expected: <A> but was: <B>
Tests run: 51, Failures: 1, Errors: 0, Skipped: 0
BUILD FAILURE
```

`> 90` үед 90 оноо A нөхцөлд орохгүй, дараагийн `>= 80` нөхцөлөөр B болдог.
Шалгалтын дараа `>= 90`-ийг сэргээж, дахин ажиллуулахад:

```text
Tests run: 51, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Дахин ажиллуулах

OpenJDK 17 буюу түүнээс дээш хувилбар, Maven шаардлагатай.
Төслийн `pom.xml` байрлах хавтсаас ажиллуулна.

Ubuntu / macOS / Bash:

```bash
mkdir -p results && mvn test 2>&1 | tee results/mvn-test.txt
echo "Maven exit code: ${PIPESTATUS[0]}"
```

Энэ Windows компьютер дээр анхны `java` команд Java 8 руу зааж байсан тул PowerShell-ийн тухайн цонхонд OpenJDK-ийг сонгоно.
Бусад компьютер дээр `JAVA_HOME`-ийг өөрийн OpenJDK суулгасан замаар солино.
Доорх командууд системийн байнгын тохиргоог өөрчлөхгүй.

```powershell
$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-21.0.1.12-hotspot'
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
java -version
mvn -version
New-Item -ItemType Directory -Path results -Force | Out-Null
mvn test 2>&1 | Tee-Object -FilePath results/mvn-test.txt
$LASTEXITCODE
```

Логуудыг PowerShell 7 ашиглан UTF-8-аар хадгалсан.
Windows PowerShell 5.1 ашигласан үед `Tee-Object`-ийн файл UTF-16 болбол UTF-8 болгон хөрвүүлж хадгална.

Мутацийг дахин шалгах дараалал:

1. `git apply results/mutation.patch` — зөвхөн `>= 90`-ийг `> 90` болгоно.
2. Bash-д `mvn test 2>&1 | tee results/mvn-test-mutant.txt`, эсвэл PowerShell-д `mvn test 2>&1 | Tee-Object -FilePath results/mvn-test-mutant.txt` ажиллуулна.
3. `ninetyIsExactlyA` унаж, `BUILD FAILURE` гарсныг шалгана.
4. `git apply -R results/mutation.patch` — өөрчлөлтийг заавал буцаана.
5. Дээрх үндсэн тестийн командыг дахин ажиллуулж, `results/mvn-test.txt`-д `BUILD SUCCESS` гарсныг шалгана.

## Ажлын дүгнэлт — 7 өгүүлбэр

Maven төслийг Java 17-д зориулан тохируулж, JUnit 5 ашиглах орчин бүрдүүлэв.
GradeCalculator нь үсгэн дүн болон таван бүрэлдэхүүний нийлбэр оноог тооцдог.
Тестүүдийг ердийн утга, хязгаарын утга, буруу оролт гэсэн бүлгээр зохион байгуулав.
Нийт 15 тестийн метод бичсэн бөгөөд параметртэй тестүүдийн мөрүүдийг тооцоход 51 гүйцэтгэл болсон.
Бүрэлдэхүүн бүрийн сөрөг болон дээд хязгаараас хэтэрсэн утгад exception шидэхийг тусад нь шалгав.
Хамгийн сонирхолтой тест болох ninetyIsExactlyA нь харьцуулалтын тэнцүүгийн тэмдгийг хасахад A-ийн оронд B гарсныг илрүүлэв.
Зориуд оруулсан алдааг буцаасны дараа бүх 51 тест амжилттай болсон бөгөөд хоёр хувилбарын бодит логийг results хавтаст хадгалав.

## Нэмэлт: AI-ийн тестийн шүүмж — 7 өгүүлбэр

Энэ төслийн код, тестийг AI туслахын дэмжлэгтэй боловсруулав.
Эхний найман тест нь зааварт дурдсан зургаан хязгаарын утга болон үсгэн дүнгийн хоёр буруу оролтыг хамарсан.
Тэр үеийн тестүүд totalScore-ыг хараахан шалгаагүй тул найман методтой болсон нь бүх шаардлагыг хангасны баталгаа байгаагүй.
Дараагийн шатанд таван бүрэлдэхүүний сөрөг ба хэтэрсэн утга, зөв нийлбэр, нэмэлт дүнгийн заагийг параметртэй тестээр шалгав.
AI-ийн нэмсэн NaN болон хязгааргүй утгын тестүүд нь энгийн харьцуулалтаар NaN-ийг алдаж болох тохиолдлыг хамарсан.
ninetyIsExactlyA тестийн чанарыг санаатай мутаци илрүүлсэн бодит үр дүнгээр баталсан бөгөөд давхардсан олон ердийн утга нэмэхээс ийм хязгаарын тест илүү ач холбогдолтой.
Нэг мутацийг илрүүлсэн нь бүх боломжит алдаа байхгүйг батлахгүй тул тестийн хүлээгдэж буй үр дүнг хэрэгжүүлэлтийг хуулбарлахгүйгээр шаардлагаас тогтоох хэрэгтэй.

## Репозиторийн бүтэц ба ажлын явц

```text
lab04-junit/
├── .gitattributes
├── .gitignore
├── pom.xml
├── README.md
├── src/main/java/mn/edu/must/sqat/GradeCalculator.java
├── src/test/java/mn/edu/must/sqat/GradeCalculatorTest.java
└── results/
    ├── java-version.txt
    ├── mvn-version.txt
    ├── mvn-test.txt
    ├── mvn-test-mutant.txt
    ├── mutation.patch
    └── mutation-summary.txt
```

Ажлын явцад Maven тохиргоо, хэрэгжүүлэлт, эхний найман тест, параметртэй тестүүд, OpenJDK баталгаажуулалт, мутацийн нотолгоог тус тусад нь commit хийсэн.
Түүхийг `git log --reverse --oneline` командаар харж болно.
`target/`, `.idea/`, `.DS_Store` нь `.gitignore`-д орсон; багшийн зааврын файл репозиторт ороогүй.
`target/surefire-reports/` нь локал тайлан бөгөөд багшид өгөх үндсэн нотолгоо нь репозиторт хадгалсан `results/` дахь хоёр Maven лог юм.

## Илгээх

README болон файлуудыг хянасны дараа public GitHub репозиторийн холбоосыг Teams Assignment-д хавсаргаж, **Turn in** дарна.
Багшийн нэмэлт мэдэгдэлд үндсэн хугацаа **2026-09-29 23:59**, дахин илгээх хугацаа **10-р сарын 4 хүртэл** гэж заасан.
Энэ README нь Teams-д илгээсэн гэсэн баталгаа биш; илгээлтийн төлөвийг Teams дээр тусад нь шалгана.
