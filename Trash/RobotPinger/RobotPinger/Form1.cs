using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net;
using System.Net.NetworkInformation;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace RobotPinger
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            this.FormClosing += closing;
            trackBar1.Value = Properties.Settings.Default.interval;
            textBox1.Text=Properties.Settings.Default.mac_pc;
            textBox2.Text=Properties.Settings.Default.mac_robot ;
            timer1.Interval = trackBar1.Value;
            timer1.Start();
        }
        private void closing(object sender, EventArgs e)
        {
            Properties.Settings.Default.Save();
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

            Properties.Settings.Default.mac_pc = textBox1.Text;
                     
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {
            Properties.Settings.Default.mac_robot = textBox2.Text;

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            // Check(textBox1.Text.ToLower().Replace(':', '-'), ref _labelRobot);
            // Check(textBox2.Text.ToLower().Replace(':', '-'), ref _labelPC);
            DoOnlyPing(textBox1.Text, ref _labelRobot);
            DoOnlyPing(textBox2.Text, ref _labelPC);

        }

        public void DoOnlyPing(string Name, ref Label l) {
            Ping pinger = new Ping();
            try
            {
                if (pinger.Send(Name).Status == IPStatus.Success)
                {
                    l.Text = "Online";
                    l.ForeColor = Color.DarkGreen;

                }
                else
                {
                    l.Text = "Offline";
                    l.ForeColor = Color.Orange;
                }
            }
            catch (Exception e)
            {
                l.Text = "Errore: " + e.Message;
                l.ForeColor = Color.DarkRed;
            }

        }

        public void Check(string Name,ref Label l)
        {
      
            try
            {

                string ris = ArpHelper.FindIpAddressByMacAddress(Name);
                Ping pinger = new Ping();
                if (ris != null)
                {
                  if( pinger.Send(ris).Status == IPStatus.Success)
                    {
                        l.Text = "On--> " + ris;
                        l.ForeColor = Color.DarkGreen;

                    }
                    else
                    {
                        l.Text = "Offline";
                        l.ForeColor = Color.Orange;
                    }
                 
                }
                else
                {
                    l.Text = "Offline";
                    l.ForeColor = Color.Orange;
                }

               
            }
            catch (Exception e)
            {
                l.Text = "Errore: " + e.Message;
                l.ForeColor = Color.DarkRed;
            }

        }

        private void trackBar1_Scroll(object sender, EventArgs e)
        {
            _labelInterval.Text = trackBar1.Value + "ms";
            timer1.Interval = trackBar1.Value;
            Properties.Settings.Default.interval= trackBar1.Value;
        }
    }
}
