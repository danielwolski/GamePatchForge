import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { catchError, of } from 'rxjs';
import { BugReport } from '../../models/bug-report.model';
import { ReportService } from '../../services/report.service';

@Component({
  selector: 'app-reports',
  imports: [RouterLink],
  templateUrl: './reports.component.html',
  styleUrl: './reports.component.css'
})
export class ReportsComponent {
  private readonly reportService = inject(ReportService);

  reports: BugReport[] = [];
  isLoading = true;
  hasError = false;

  constructor() {
    this.loadReports();
  }

  loadReports(): void {
    this.isLoading = true;
    this.hasError = false;
    this.reportService.getReports().pipe(
      catchError(() => {
        this.hasError = true;
        return of([] as BugReport[]);
      })
    ).subscribe((reports) => {
      this.reports = reports;
      this.isLoading = false;
    });
  }

  trackReport(_index: number, report: BugReport): string {
    return report.uuid;
  }
}
