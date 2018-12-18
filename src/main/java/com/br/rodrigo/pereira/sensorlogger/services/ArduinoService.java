package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import jssc.SerialPort;
import jssc.SerialPortList;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ArduinoService {

    public String readMeasures() {
        String rawData;

        SerialPort serialPort = connectArduino();
        try {
            serialPort.writeBytes(Character.toString('r').getBytes());
            TimeUnit.MILLISECONDS.sleep(1000);
            byte buffer[];
            byte[] measuresByte = new byte[22];
            int i = 0;
            String aux;
            do {
                TimeUnit.MILLISECONDS.sleep(10);
                buffer = serialPort.readBytes(1);
                measuresByte[i] = buffer[0];
                i++;
                aux = new String(buffer);
            } while (!aux.equals("\r") && i < 22);

            TimeUnit.MILLISECONDS.sleep(100);
            serialPort.closePort();
            rawData = new String(measuresByte);
        } catch (Exception ex) {
            throw new BusinessException();
        }
        rawData = rawData.replaceAll("\\r", "");
        rawData = rawData.replaceAll(Character.toString('\0'),"");
        return rawData;
    }

    public Boolean selectBomb(String option) {
        String rawData;
        SerialPort serialPort = connectArduino();

        try {
            if (option.equals("Ligar")) {
                serialPort.writeBytes(Character.toString('t').getBytes());
            }
            if (option.equals("Desligar")) {
                serialPort.writeBytes(Character.toString('o').getBytes());
            }
            TimeUnit.MILLISECONDS.sleep(10);
            byte[] buffer = serialPort.readBytes(1);
            TimeUnit.MILLISECONDS.sleep(100);
            serialPort.closePort();
            rawData = new String(buffer);
        } catch (Exception ex) {
            throw new BusinessException();
        }
        return rawData.equals("L");
    }

    private SerialPort connectArduino() {
        String port = SerialPortList.getPortNames()[0];
        SerialPort serialPort = new SerialPort(port);
        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return serialPort;
    }
}
