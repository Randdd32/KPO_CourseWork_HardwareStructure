import ServerIcon from "../../assets/icons/deviceTypes/device-type-1.png"
import PCIcon from "../../assets/icons/deviceTypes/device-type-2.png"
import LaptopIcon from "../../assets/icons/deviceTypes/device-type-3.png"
import MonitorIcon from "../../assets/icons/deviceTypes/device-type-4.png"
import PrinterIcon from "../../assets/icons/deviceTypes/device-type-5.png"
import RouterIcon from "../../assets/icons/deviceTypes/device-type-6.png"
import ProjectorIcon from "../../assets/icons/deviceTypes/device-type-7.png"
import TelephoneIcon from "../../assets/icons/deviceTypes/device-type-8.png"
import PlaceholderIcon from "../../assets/icons/placeholder.png"

const deviceImageMap = {
    'сервер': ServerIcon,
    'персональный компьютер': PCIcon,
    'ноутбук': LaptopIcon,
    'монитор': MonitorIcon,
    'принтер': PrinterIcon,
    'роутер': RouterIcon,
    'проектор': ProjectorIcon,
    'телефон': TelephoneIcon,
    'default': PlaceholderIcon 
};

export const getDeviceImage = (typeName) => {
  const normalizedTypeName = typeName.toLowerCase();
  return deviceImageMap[normalizedTypeName] || deviceImageMap['default'];
};