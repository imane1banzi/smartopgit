import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {DashboardService} from "../services/dashboard.service";
import {ChartData, ChartOptions, ChartType} from 'chart.js';
import {OperationDTO} from "../services/operation.service";
import {CRDTO} from "../services/cr.service";
import {forkJoin} from "rxjs";



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  public pieChartOptions: ChartOptions = {
    responsive: true,
  };
  public pieChartLabels: string[] = ['Operations', 'CRs'];
  public pieChartType: ChartType = 'pie'; // ou 'doughnut' pour un graphique en anneau
  public pieChartLegend = true;
  public pieChartPlugins = [];

  public pieChartData: ChartData<'pie'> = {
    labels: this.pieChartLabels,
    datasets: [
      {
        data: [0, 0],
        label: 'Count',
        backgroundColor: ['#5C5959FF', '#87A8BEFF'], // Personnaliser les couleurs
        hoverBackgroundColor: ['#5C5959FF', '#87A8BEFF']
      }
    ]
  };

  constructor(private dashboardService: DashboardService, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    let crCount = 0;
    let opCount = 0;

    this.dashboardService.getData1().subscribe((crData: CRDTO[]) => {
      crCount = crData.length;
      console.log('CR Count:', crCount);
      this.updateChart(crCount, opCount);
    });

    this.dashboardService.getData2().subscribe((operationData: OperationDTO[]) => {
      opCount = operationData.length;
      console.log('Operation Count:', opCount);
      this.updateChart(crCount, opCount);
    });
  }

  private updateChart(crCount: number, opCount: number): void {
    this.pieChartData.datasets[0].data = [opCount, crCount];
    console.log('Updated Chart Data:', this.pieChartData.datasets[0].data);
    setTimeout(() => {
      this.cdr.detectChanges(); // Détecter les changements et forcer une mise à jour
    }, 100);
  }
}
