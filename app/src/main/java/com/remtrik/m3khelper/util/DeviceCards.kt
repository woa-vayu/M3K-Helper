package com.remtrik.m3khelper.util

import com.remtrik.m3khelper.M3KApp
import com.remtrik.m3khelper.R

data class DeviceCard(
    val deviceCodename: Array<String>,
    val deviceName: String,
    val deviceImage: Int,
    val deviceGuide: String,
    val deviceGroup: String,
    val deviceDrivers: String,
    val deviceUEFI: String,
    val noModem: Boolean, val noFlash: Boolean,
    val noBoot: Boolean, val noMount: Boolean,
    val sensors: Boolean, val noGuide: Boolean,
    val noGroup: Boolean, val noDrivers: Boolean,
    val noUEFI: Boolean, val unifiedDriversUEFI: Boolean
)

val vayuCard = DeviceCard(
    arrayOf("vayu"),
    "POCO X3 Pro",
    R.drawable.vayu,
    "https://github.com/woa-vayu/POCOX3Pro-Guides",
    "https://t.me/winonvayualt",
    "https://github.com/woa-vayu/POCOX3Pro-Releases/releases/latest",
    "",
    true, false,
    false, false,
    false, false,
    false, false,
    false, true
)

val bhimaCard = vayuCard.copy(deviceCodename = arrayOf("bhima"), deviceName = "POCO X3 Pro")

val nabuCard = DeviceCard(
    arrayOf("nabu"),
    "Xiaomi Pad 5",
    R.drawable.nabu,
    "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5",
    "https://t.me/nabuwoa",
    "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5/releases/tag/Drivers",
    "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5/releases/tag/UEFI",
    true, false,
    false, false,
    false, false,
    false, false,
    false, false
)

val raphaelCard = DeviceCard(
    arrayOf("raphael"),
    "Xiaomi Mi 9T Pro",
    R.drawable.raphael,
    "https://github.com/graphiks/woa-raphael",
    "https://t.me/woaraphael",
    "https://github.com/woa-raphael/raphael-drivers/releases/latest",
    "https://github.com/woa-raphael/woa-raphael/releases/tag/raphael-uefi",
    false, false,
    false, false,
    true, false,
    false, false,
    false, false
)

val raphaelinCard = raphaelCard.copy(deviceCodename = arrayOf("raphaelin"), deviceName = "Redmi K20 Pro")
val raphaelsCard = raphaelCard.copy(deviceCodename = arrayOf("raphaels"), deviceName = "Redmi K20 Pro Premium")

val cepheusCard = DeviceCard(
    arrayOf("chepheus"),
    "Xiaomi Mi 9",
    R.drawable.cepheus,
    "https://github.com/ivnvrvnn/Port-Windows-XiaoMI-9",
    "https://t.me/woacepheus",
    "https://github.com/qaz6750/XiaoMi9-Drivers/releases/latest",
    "",
    true, false,
    false, false,
    false, false,
    false, false,
    false, true
)

val berylliumCard = DeviceCard(
    arrayOf("beryllium"),
    "POCO F1",
    R.drawable.beryllium,
    "https://github.com/n00b69/woa-beryllium",
    "https://t.me/WinOnF1",
    "https://github.com/n00b69/woa-beryllium/releases/tag/Drivers",
    "https://github.com/n00b69/woa-beryllium/releases/tag/UEFI",
    false, false,
    false, false,
    false, false,
    false, false,
    false, false
)

val miatolCard = DeviceCard(
    arrayOf("miatoll"),
    "Xiaomi Redmi Note 9 Pro",
    R.drawable.miatoll,
    "https://github.com/woa-miatoll/Port-Windows-11-Redmi-Note-9-Pro",
    "http://t.me/woamiatoll",
    "https://github.com/woa-miatoll/Miatoll-Releases/releases/latest",
    "",
    true, false,
    false, false,
    false, false,
    false, false,
    false, true
)

val durandalCard = miatolCard.copy(deviceCodename = arrayOf("durandal"), deviceName = "Xiaomi Redmi Note 9S")
val curtana_indiaCard = durandalCard.copy(deviceCodename = arrayOf("curtana_india"), deviceName = "Xiaomi Redmi Note 9S")
val curtanaCard = miatolCard.copy(deviceCodename = arrayOf("curtana"), deviceName = "Xiaomi Redmi Note 9S NFC")
val excaliburCard = miatolCard.copy(deviceCodename = arrayOf("excalibur"), deviceName = "Xiaomi Redmi Note 9 Pro Max")
val gramCard = miatolCard.copy(deviceCodename = arrayOf("gram"), deviceName = "POCO M2 Pro")
val joyeuseCard = miatolCard.copy(deviceCodename = arrayOf("joyeuse"), deviceName = "Xiaomi Redmi Note 9 Pro")

val alphaCard = DeviceCard(
    arrayOf("alpha"),
    "LG G8",
    R.drawable.alpha,
    "https://github.com/n00b69/woa-alphaplus",
    "https://t.me/lgedevices",
    "https://github.com/n00b69/woa-alphaplus/releases/tag/Drivers",
    "https://github.com/n00b69/woa-alphaplus/releases/tag/UEFI",
    false, false,
    false, false,
    false, false,
    false, false,
    false, false
)

val mh2lm5gCard = DeviceCard(
    arrayOf("mh2lm5g"),
    "LG V50S",
    R.drawable.mh2,
    "https://github.com/n00b69/woa-mh2lm5g",
    "https://t.me/lgedevices",
    "https://github.com/n00b69/woa-mh2lm5g/releases/tag/Drivers",
    "https://github.com/n00b69/woa-mh2lm5g/releases/tag/UEFI",
    false, false,
    false, false,
    false, false,
    false, false,
    false, false
)

val mh2Card = mh2lm5gCard.copy(deviceCodename = arrayOf("mh2"), deviceName = "LG V50S", deviceGuide = "https://github.com/n00b69/woa-mh2lm")

val betaCard = DeviceCard(
    arrayOf("beta"),
    "LG G8S",
    R.drawable.beta,
    "https://github.com/n00b69/woa-betalm",
    "https://t.me/lgedevices",
    "https://github.com/n00b69/woa-betalm/releases/tag/Drivers",
    "https://github.com/n00b69/woa-betalm/releases/tag/UEFI",
    true, false,
    false, false,
    false, false,
    false, false,
    false, false
)

val flashCard = mh2lm5gCard.copy(deviceCodename = arrayOf("flash"), deviceName = "LG V50", deviceGuide = "https://github.com/n00b69/woa-flashlmdd", deviceImage = R.drawable.flashlm)

val guacamoleCard = DeviceCard(
    arrayOf("guacamole", "OnePlus7Pro"),
    "OnePlus 7 Pro",
    R.drawable.guacamole,
    "",
    "https://t.me/onepluswoachat",
    "",
    "",
    true, true,
    true, false,
    false, true,
    false, true,
    true, false
)

val hotdogCard = guacamoleCard.copy(deviceCodename = arrayOf("hotdog", "OnePlus7TPro"), deviceName = "OnePlus 7T Pro", deviceImage = R.drawable.hotdog)

val suryaCard = DeviceCard(
    arrayOf("surya"),
    "POCO X3 NFC",
    R.drawable.vayu,
    "https://github.com/woa-surya/POCOX3NFC-Guides",
    "https://t.me/windows_on_pocox3_nfc",
    "",
    "",
    true, true,
    true, false,
    false, true,
    true, true,
    true, true
)

val karnaCard = suryaCard.copy(deviceCodename = arrayOf("karna"), deviceName = "POCO X3")

val a52sxqCard = DeviceCard(
    arrayOf("a52sxq"),
    "Samsung Galaxy A52s",
    R.drawable.a52sxq,
    "https://github.com/woa-a52s/Samsung-A52s-5G-Guides",
    "https://t.me/a52sxq_uefi",
    "https://github.com/woa-a52s/Samsung-A52s-5G-Releases/releases/latest",
    "",
    true, false,
    false, false,
    false, false,
    false, false,
    false, true
)

val emu64xaCard = DeviceCard(
    arrayOf("emu64xa"),
    "emu64xa",
    R.drawable.vayu,
    "https://google.com",
    "https://google.com",
    "https://google.com",
    "https://google.com",
    false, false,
    false, false,
    false, false,
    false, false,
    false, true
)

val unknownCard = DeviceCard(
    arrayOf("unknown"),
    M3KApp.getString(R.string.unknown_device),
    R.drawable.ic_device_unknown,
    "",
    "",
    "",
    "",
    false, false,
    false, false,
    false, true,
    true, true,
    true, false
)
